package edu.kwjw.you.presentation

sealed class UiEvent {
    data class ShowDialogSnackbar(val message: String) : UiEvent()
    data class ShowListSnackbar(val message: String) : UiEvent()
    object DismissDialog : UiEvent()
}
