package edu.kwjw.you.presentation.uiState

import edu.kwjw.you.presentation.ui.addnewevent.Time
import edu.kwjw.you.util.validation.InputTextValidator

data class AddEventState(
    val uiState: AddEventUiState,
    val name: String = "",
    val isNameError: Boolean = false,
    val nameErrorType: InputTextValidator.Error? = null,
    val rawDate: String = "",
    val dateTimestamp: Long? = null,
    val isDateError: Boolean = false,
    val rawTime: String = "",
    val time: Time? = null,
    val isTimeError: Boolean = false,
)

sealed interface AddEventUiState {
    data object Idle : AddEventUiState
    data object Error : AddEventUiState
    data object Success : AddEventUiState
}