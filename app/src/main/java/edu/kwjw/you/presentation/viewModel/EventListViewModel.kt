package edu.kwjw.you.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.domain.model.PlainEventData
import edu.kwjw.you.domain.usecase.AddNewEvent
import edu.kwjw.you.domain.usecase.GetUserEvent
import edu.kwjw.you.exceptions.InvalidEventDataException
import edu.kwjw.you.presentation.ui.eventlist.toEventItemList
import edu.kwjw.you.presentation.uiState.UiEvent
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

    private val _uiEvent = MutableLiveData<UiEvent>()
    val uiEvent: LiveData<UiEvent> get() = _uiEvent

    private val _userId = MutableLiveData(1)
    val userId: LiveData<Int> = _userId

    fun getEvents(userId: Int) {
        viewModelScope.launch {
            getUserEvent(userId).collectLatest {
                _uiEvent.value = when (it) {
                    is ApiResult.HttpError, is ApiResult.NetworkError -> UiEvent.EventListUpdate(
                        state = UiEvent.EventListUpdate.EventState.ERROR
                    )

                    ApiResult.Loading -> UiEvent.EventListUpdate()
                    is ApiResult.Success -> UiEvent.EventListUpdate(
                        data = it.data.toEventItemList(),
                        state = UiEvent.EventListUpdate.EventState.SUCCESS
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
                            _uiEvent.value = UiEvent.DismissDialog
                            _uiEvent.value =
                                UiEvent.ShowListSnackbar("Successfully added new event")

                        }

                        else -> {
                            /*Loading*/
                        }
                    }
                }
            } catch (e: InvalidEventDataException) {
                _uiEvent.value = UiEvent.ShowDialogSnackbar("Invalid data")
            }
        }
    }
}