package edu.kwjw.you.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.domain.usecase.GetUserEvent
import edu.kwjw.you.presentation.ui.eventlist.toEventItemList
import edu.kwjw.you.presentation.uiState.UiEvent
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getUserEvent: GetUserEvent
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
}