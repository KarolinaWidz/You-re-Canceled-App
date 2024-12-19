package edu.kwjw.you.presentation.viewModel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.domain.usecase.addnewevent.AddNewEvent
import edu.kwjw.you.domain.usecase.addnewevent.FormatDate
import edu.kwjw.you.domain.usecase.addnewevent.FormatTime
import edu.kwjw.you.domain.usecase.addnewevent.ValidateEventName
import edu.kwjw.you.presentation.ui.addnewevent.Time
import edu.kwjw.you.presentation.uiState.AddEventIntent
import edu.kwjw.you.presentation.uiState.AddEventState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val addNewEvent: AddNewEvent,
    private val validateEventName: ValidateEventName,
    private val formatDate: FormatDate,
    private val formatTime: FormatTime,
) : ViewModel() {
    private val _userId = mutableIntStateOf(1)
    
    private val _state = MutableStateFlow(AddEventState())
    val state: StateFlow<AddEventState> = _state

    fun processIntent(intent: AddEventIntent) {
        when (intent) {
            is AddEventIntent.UpdateName -> updateName(name = intent.name)
            is AddEventIntent.UpdateDate -> updateDate(timestamp = intent.timestamp)
            is AddEventIntent.UpdateTime -> updateTime(time = intent.time)
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
                rawDate = rawDate
            )
        }
    }


    private fun updateTime(time: Time?) {
        val rawTime = formatTime.execute(time)
        _state.update { state ->
            state.copy(
                timeTimestamp = time,
                rawTime = rawTime
            )
        }
    }
}