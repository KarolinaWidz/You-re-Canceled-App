package edu.kwjw.you.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.domain.usecase.GetUserEvent
import edu.kwjw.you.presentation.ui.eventlist.toEventItemList
import edu.kwjw.you.presentation.uiState.EventListIntent
import edu.kwjw.you.presentation.uiState.EventListState
import edu.kwjw.you.presentation.uiState.EventListUiState
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

    private val _state =
        MutableStateFlow(EventListState(uiState = EventListUiState.Loading, userId = 1))
    val state: StateFlow<EventListState> = _state

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