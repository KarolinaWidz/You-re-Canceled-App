package edu.kwjw.you.presentation.viewModel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.domain.usecase.addnewevent.AddNewEvent
import edu.kwjw.you.domain.usecase.addnewevent.FormatDate
import edu.kwjw.you.domain.usecase.addnewevent.FormatTime
import edu.kwjw.you.domain.usecase.addnewevent.ValidateEventName
import edu.kwjw.you.presentation.ui.addnewevent.Time
import edu.kwjw.you.presentation.uiState.AddEventIntent
import edu.kwjw.you.presentation.uiState.AddEventState
import edu.kwjw.you.presentation.uiState.UiState
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val addNewEvent: AddNewEvent,
    private val validateEventName: ValidateEventName,
    private val formatDate: FormatDate,
    private val formatTime: FormatTime,
) : ViewModel() {
    private val _userId = mutableIntStateOf(1)

    private val _state = MutableStateFlow(AddEventState(uiState = UiState.Idle))
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
        val result = validateEventName.execute(name)
        _state.update { state ->
            state.copy(
                name = name,
                isNameError = !result.isSuccessful,
                nameErrorType = result.error
            )
        }
    }

    private fun updateDate(timestamp: Long?) {
        val rawDate = formatDate.execute(timestamp)
        _state.update { state ->
            state.copy(
                dateTimestamp = timestamp,
                rawDate = rawDate,
                isDateError = false
            )
        }
    }


    private fun updateTime(time: Time?) {
        val rawTime = formatTime.execute(time)
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
                    uiState = UiState.Error
                )
            }
        } else {
            viewModelScope.launch {
                addNewEvent.execute(
                    userId = _userId.value,
                    dateTimestamp = requireNotNull(_state.value.dateTimestamp) { "If no error, shouldn't be null" },
                    time = requireNotNull(_state.value.time) { "If no error, shouldn't be null" },
                    _state.value.name
                )
                    .collectLatest { result ->
                        _state.update { state ->
                            state.copy(
                                uiState = if (result is ApiResult.Success) UiState.Success else UiState.Error
                            )
                        }
                    }
            }
        }
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
}