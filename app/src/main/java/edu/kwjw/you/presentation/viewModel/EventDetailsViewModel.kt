package edu.kwjw.you.presentation.viewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.data.repository.event.EventRepository
import edu.kwjw.you.presentation.uiState.EventDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    eventId: String,
    eventRepository: EventRepository
) {
    private val _state = MutableStateFlow(EventDetailsState(eventId = eventId))
    val state: StateFlow<EventDetailsState> = _state
}