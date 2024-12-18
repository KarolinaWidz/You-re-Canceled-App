package edu.kwjw.you.presentation.uiState

import edu.kwjw.you.domain.usecase.common.ValidationError

data class AddEventState(
    val isSaveError: Boolean = false,
    val name: String = "",
    val isNameError: Boolean = false,
    val nameErrorType: ValidationError? = null,
    val rawDate: String = "",
    val isDateError: Boolean = false,
    val rawTime: String = "",
    val isTimeError: Boolean = false
)