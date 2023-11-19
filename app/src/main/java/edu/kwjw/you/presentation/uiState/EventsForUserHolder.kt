package edu.kwjw.you.presentation.uiState

import edu.kwjw.you.domain.model.Event

data class EventsForUserHolder(
    val data: List<Event> = emptyList(),
    val state: EventState = EventState.LOADING
) {
    enum class EventState { LOADING, SUCCESS, ERROR }
}
