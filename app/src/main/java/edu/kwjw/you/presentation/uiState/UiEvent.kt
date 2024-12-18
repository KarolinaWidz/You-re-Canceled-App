package edu.kwjw.you.presentation.uiState

import edu.kwjw.you.presentation.ui.eventlist.EventItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class UiEvent {
    data class ShowDialogSnackbar(val message: String) : UiEvent()
    data class ShowListSnackbar(val message: String) : UiEvent()
    object DismissDialog : UiEvent()
    data class EventListUpdate(
        val data: ImmutableList<EventItem> = persistentListOf(),
        val state: EventState = EventState.LOADING
    ) : UiEvent() {
        enum class EventState { LOADING, SUCCESS, ERROR }
    }
}


