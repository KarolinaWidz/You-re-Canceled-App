package edu.kwjw.you.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.data.model.Event
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.util.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(private val repository: EventRepository) :
    ViewModel() {
    private val _events = MutableLiveData<Result<List<Event>>>()
    val events: LiveData<Result<List<Event>>> = _events


    fun getEventsForUser(userId: Int) {
        viewModelScope.launch {
            _events.value = Result.Loading
            _events.value = repository.getEventsForUser(userId)
        }
    }
}