package edu.kwjw.you.presentation.uiState

import edu.kwjw.you.data.repository.model.Event

data class EventDetailsState(
    val uiState: EventDetailsUiState = EventDetailsUiState.Loading,
    val eventId: String,
    val event: Event? = null,
)

sealed interface EventDetailsUiState {
    data object Loading : EventDetailsUiState
    data object Idle : EventDetailsUiState
}