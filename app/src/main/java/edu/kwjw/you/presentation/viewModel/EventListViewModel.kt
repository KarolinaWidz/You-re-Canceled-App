package edu.kwjw.you.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.domain.usecase.EventList
import edu.kwjw.you.presentation.EventsForUserHolder
import edu.kwjw.you.util.Result
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(private val eventList: EventList) :
    ViewModel() {
    private val _eventsState = MutableLiveData<EventsForUserHolder>()
    val eventsState: LiveData<EventsForUserHolder> = _eventsState


    fun getEventsForUser(userId: Int) {
        viewModelScope.launch {
            _eventsState.value = EventsForUserHolder(listOf(), EventsForUserHolder.EventState.LOADING)
            val result = eventList.getEventsForUser(userId).last()
            _eventsState.value = when (result) {
                is Result.HttpError, is Result.NetworkError -> EventsForUserHolder(
                    listOf(),
                    EventsForUserHolder.EventState.ERROR
                )

                is Result.Loading -> EventsForUserHolder(
                    listOf(),
                    EventsForUserHolder.EventState.LOADING
                )

                is Result.Success -> EventsForUserHolder(
                    result.data,
                    EventsForUserHolder.EventState.SUCCESS
                )
            }
        }
    }
}