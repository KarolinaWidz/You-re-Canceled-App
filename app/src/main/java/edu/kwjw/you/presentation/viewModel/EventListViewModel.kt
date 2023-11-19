package edu.kwjw.you.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.domain.usecase.AddNewEvent
import edu.kwjw.you.domain.usecase.GetEventsForUser
import edu.kwjw.you.presentation.uiState.EventsForUserHolder
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getEventsForUser: GetEventsForUser,
    private val addNewEvent: AddNewEvent
) :
    ViewModel() {
    private val _eventsState = MutableLiveData(EventsForUserHolder())
    val eventsState: LiveData<EventsForUserHolder> = _eventsState

    private val _addEventApiResult = MutableLiveData<ApiResult<Event>>()
    val addEventApiResult: LiveData<ApiResult<Event>> get() = _addEventApiResult

    private val _userId = MutableLiveData(1)
    val userId: LiveData<Int> = _userId

    fun getEvents(userId: Int) {
        viewModelScope.launch {
            getEventsForUser(userId).collectLatest {
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

    fun addNewEvent(nameText: String, dateText: String, timeText: String) {
        viewModelScope.launch {
            addNewEvent(userId.value!!, nameText).collectLatest {
                _addEventApiResult.value = it
            }
        }
    }
}