package edu.kwjw.you.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.domain.model.PlainEventData
import edu.kwjw.you.domain.usecase.NewEvent
import edu.kwjw.you.domain.usecase.UserEvents
import edu.kwjw.you.presentation.uiState.EventsForUserHolder
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val userEvents: UserEvents,
    private val newEvent: NewEvent
) :
    ViewModel() {
    private val _eventsState = MutableLiveData<EventsForUserHolder>()
    val eventsState: LiveData<EventsForUserHolder> = _eventsState

    private val _addEventApiResult = MutableLiveData<ApiResult<Event>>()
    val addEventApiResult: LiveData<ApiResult<Event>> get() = _addEventApiResult

    private val _userId = MutableLiveData(1)
    val userId: LiveData<Int> = _userId

    fun getEvents(userId: Int) {
        viewModelScope.launch {
            userEvents.get(userId).collectLatest {
                _eventsState.value = when (it) {
                    is ApiResult.HttpError, is ApiResult.NetworkError -> EventsForUserHolder(
                        state = EventsForUserHolder.EventState.ERROR
                    )

                    is ApiResult.Loading -> EventsForUserHolder()

                    is ApiResult.Success -> EventsForUserHolder(
                        data = it.data,
                        state = EventsForUserHolder.EventState.SUCCESS
                    )
                }
            }

        }
    }

    fun isNewEventValid(plainEventData: PlainEventData): Boolean {
        return newEvent.isValid(plainEventData)
    }

    fun addNewEvent(plainEventData: PlainEventData) {
        viewModelScope.launch {
            newEvent.add(userId.value!!, plainEventData).collectLatest {
                _addEventApiResult.value = it

            }
        }
    }
}