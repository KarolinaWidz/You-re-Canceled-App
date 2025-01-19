package edu.kwjw.you.presentation.uiState

import edu.kwjw.you.domain.usecase.common.ValidationError
import edu.kwjw.you.presentation.ui.addnewevent.Time

data class AddEventState(
    val uiState: UiState,
    val name: String = "",
    val isNameError: Boolean = false,
    val nameErrorType: ValidationError? = null,
    val rawDate: String = "",
    val dateTimestamp: Long? = null,
    val isDateError: Boolean = false,
    val rawTime: String = "",
    val time: Time? = null,
    val isTimeError: Boolean = false,
)

sealed interface UiState {
    data object Idle : UiState
    data object Error : UiState
    data object Success : UiState
}