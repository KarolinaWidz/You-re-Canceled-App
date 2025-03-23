package edu.kwjw.you.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.domain.usecase.GetUserEvent
import edu.kwjw.you.presentation.ui.eventlist.toEventItemList
import edu.kwjw.you.presentation.uiState.EventListIntent
import edu.kwjw.you.presentation.uiState.EventListState
import edu.kwjw.you.presentation.uiState.EventListUiState
import edu.kwjw.you.presentation.uiState.UiEvent
import edu.kwjw.you.util.ApiResult
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getUserEvent: GetUserEvent
) : ViewModel() {

    private val _state = MutableStateFlow(EventListState(uiState = EventListUiState.Loading))
    val state: StateFlow<EventListState> = _state

    private val _uiEvent = MutableLiveData<UiEvent>()
    val uiEvent: LiveData<UiEvent> get() = _uiEvent

    private val _userId = MutableLiveData(1)
    val userId: LiveData<Int> = _userId

    fun processIntent(intent: EventListIntent) {
        when (intent) {
            is EventListIntent.GetEvents -> getEvents(intent.userId)
        }
    }

    private fun getEvents(userId: Int) {
        viewModelScope.launch {
            getUserEvent.execute(userId).collectLatest { result ->
                _state.update { state ->
                    when (result) {
                        ApiResult.Loading -> state.copy(uiState = EventListUiState.Loading)
                        is ApiResult.NetworkError, is ApiResult.HttpError -> state.copy(
                            uiState = EventListUiState.Error,
                            events = persistentListOf()
                        )

                        is ApiResult.Success -> state.copy(
                            uiState = EventListUiState.Success,
                            events = result.data.toEventItemList()
                        )
                    }
                }
            }
        }
    }
}