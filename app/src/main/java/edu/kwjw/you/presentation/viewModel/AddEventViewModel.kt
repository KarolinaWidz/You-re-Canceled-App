package edu.kwjw.you.presentation.viewModel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.presentation.ui.addnewevent.Time
import edu.kwjw.you.presentation.uiState.AddEventIntent
import edu.kwjw.you.presentation.uiState.AddEventState
import edu.kwjw.you.presentation.uiState.AddEventUiState
import edu.kwjw.you.presentation.util.toFullDateString
import edu.kwjw.you.presentation.util.toTimeString
import edu.kwjw.you.util.ApiResult
import edu.kwjw.you.util.ValidationError
import edu.kwjw.you.util.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {
    private val _userId = mutableIntStateOf(1)

    private val _state = MutableStateFlow(AddEventState(uiState = AddEventUiState.Idle))
    val state: StateFlow<AddEventState> = _state

    fun processIntent(intent: AddEventIntent) {
        when (intent) {
            is AddEventIntent.UpdateName -> updateName(name = intent.name)
            is AddEventIntent.UpdateDate -> updateDate(timestamp = intent.timestamp)
            is AddEventIntent.UpdateTime -> updateTime(time = intent.time)
            AddEventIntent.SaveEvent -> saveEvent()
        }
    }

    private fun updateName(name: String) {
        val result = validateEventName(name)
        _state.update { state ->
            state.copy(
                name = name,
                isNameError = !result.isSuccessful,
                nameErrorType = result.error
            )
        }
    }

    private fun updateDate(timestamp: Long?) {
        val rawDate = timestamp?.toFullDateString() ?: ""
        _state.update { state ->
            state.copy(
                dateTimestamp = timestamp,
                rawDate = rawDate,
                isDateError = false
            )
        }
    }


    private fun updateTime(time: Time?) {
        val rawTime = time?.toTimeString() ?: ""
        _state.update { state ->
            state.copy(
                time = time,
                rawTime = rawTime,
                isTimeError = false
            )
        }
    }


    private fun saveEvent() {
        validateDateAndTime()
        if (_state.value.isDateError || _state.value.isTimeError || _state.value.isNameError) {
            _state.update { state ->
                state.copy(
                    uiState = AddEventUiState.Error
                )
            }
            Log.e(LOG_TAG, "Event cannot be saved because of error: ${state.value}")
        } else {
            viewModelScope.launch {
                val result = repository.addEvent(
                    userId = _userId.intValue,
                    dateTimestamp = requireNotNull(_state.value.dateTimestamp) { "If no error, shouldn't be null" },
                    time = requireNotNull(_state.value.time) { "If no error, shouldn't be null" },
                    _state.value.name
                )
                _state.update { state ->
                    state.copy(uiState = if (result is ApiResult.Success) AddEventUiState.Success else AddEventUiState.Error)
                }
            }
        }
    }

    private fun validateEventName(name: String): ValidationResult {
        if (name.isBlank()) {
            Log.w(LOG_TAG, "Name is blank")
            return ValidationResult(isSuccessful = false, error = ValidationError.Empty)
        }
        if (name.length > 100) {
            Log.w(LOG_TAG, "Maximum length of name exceeded")
            return ValidationResult(
                isSuccessful = false,
                error = ValidationError.MaxLengthExceeded
            )
        }
        return ValidationResult(isSuccessful = true)
    }

    private fun validateDateAndTime() {
        _state.update { state ->
            state.copy(
                isNameError = _state.value.name.isBlank(),
                isTimeError = _state.value.time == null,
                isDateError = _state.value.dateTimestamp == null
            )
        }
    }

    companion object {
        const val LOG_TAG = "AddEventViewModel"
    }
}