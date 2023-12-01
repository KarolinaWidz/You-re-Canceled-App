package edu.kwjw.you.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.domain.model.PlainEventData
import edu.kwjw.you.domain.usecase.AddNewEvent
import edu.kwjw.you.domain.usecase.GetUserEvent
import edu.kwjw.you.exceptions.InvalidEventDataException
import edu.kwjw.you.presentation.UiEvent
import edu.kwjw.you.presentation.uiState.EventsForUserHolder
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getUserEvent: GetUserEvent,
    private val addNewEvent: AddNewEvent
) :
    ViewModel() {
    private val _eventsState = MutableLiveData<EventsForUserHolder>()
    val eventsState: LiveData<EventsForUserHolder> = _eventsState

    private val _addEventApiResult = MutableLiveData<ApiResult<Event>>()
    val addEventApiResult: LiveData<ApiResult<Event>> get() = _addEventApiResult

    private val _uiEvent = MutableLiveData<UiEvent>()
    val uiEvent: LiveData<UiEvent> get() = _uiEvent

    private val _userId = MutableLiveData(1)
    val userId: LiveData<Int> = _userId

    fun getEvents(userId: Int) {
        viewModelScope.launch {
            getUserEvent(userId).collectLatest {
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

    fun addNewEvent(plainEventData: PlainEventData) {
        viewModelScope.launch {
            try {
                addNewEvent(userId.value!!, plainEventData).collectLatest {
                    when (it) {
                        is ApiResult.HttpError -> _uiEvent.value =
                            UiEvent.ShowDialogSnackbar("Server error. Please try again")

                        is ApiResult.NetworkError -> _uiEvent.value =
                            UiEvent.ShowDialogSnackbar("Network issues. Please try again.")

                        is ApiResult.Success -> {
                            _uiEvent.value =
                                UiEvent.ShowListSnackbar("Successfully added new event")
                            _addEventApiResult.value = it
                        }

                        else -> {
                            /*loading*/
                        }
                    }
                }
            } catch (e: InvalidEventDataException) {
                _uiEvent.value = UiEvent.ShowDialogSnackbar("Invalid data")
            }
        }
    }
}