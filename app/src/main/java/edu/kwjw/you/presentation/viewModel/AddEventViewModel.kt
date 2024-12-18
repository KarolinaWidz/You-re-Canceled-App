package edu.kwjw.you.presentation.viewModel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.domain.usecase.addnewevent.AddNewEvent
import edu.kwjw.you.domain.usecase.addnewevent.ValidateEventName
import edu.kwjw.you.presentation.uiState.AddEventIntent
import edu.kwjw.you.presentation.uiState.AddEventState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val addNewEvent: AddNewEvent,
    private val validateEventName: ValidateEventName
) : ViewModel() {
    private val _userId = mutableIntStateOf(1)
    private val _state = MutableStateFlow(AddEventState())
    val state: StateFlow<AddEventState> = _state

    fun processIntent(intent: AddEventIntent) {
        when (intent) {
            is AddEventIntent.UpdateName -> updateName(name = intent.name)
        }
    }

    private fun updateName(name: String) {
        val result = validateEventName.execute(name)
        _state.update { state ->
            state.copy(
                name = name,
                isNameError = result.isSuccessful,
                nameErrorType = result.error
            )
        }
    }
}