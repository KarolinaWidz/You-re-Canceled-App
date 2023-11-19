package edu.kwjw.you.presentation

import edu.kwjw.you.domain.model.Event

data class EventsForUserHolder(val data: List<Event>, val state: EventState) {
    enum class EventState { LOADING, SUCCESS, ERROR }
}
