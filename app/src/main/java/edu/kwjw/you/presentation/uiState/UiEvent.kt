package edu.kwjw.you.presentation.uiState

import edu.kwjw.you.domain.model.Event

sealed class UiEvent {
    data class ShowDialogSnackbar(val message: String) : UiEvent()
    data class ShowListSnackbar(val message: String) : UiEvent()
    object DismissDialog : UiEvent()
    data class EventListUpdate(
        val data: List<Event> = emptyList(),
        val state: EventState = EventState.LOADING
    ) : UiEvent() {
        enum class EventState { LOADING, SUCCESS, ERROR }
    }
}


