package edu.kwjw.you.presentation.uiState

import edu.kwjw.you.presentation.ui.eventlist.EventItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class EventListState(
    val uiState: EventListUiState,
    val events: ImmutableList<EventItem> = persistentListOf()

)

sealed interface EventListUiState {
    data object Success : EventListUiState
    data object Error : EventListUiState
    data object Loading : EventListUiState
}