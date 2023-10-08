package edu.kwjw.you.ui.viewModel

import android.util.Log
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

private const val TAG = "EventListViewModel"

@HiltViewModel
class EventListViewModel @Inject constructor(private val repository: EventRepository) :
    ViewModel() {
    private val _events = MutableLiveData<Result<List<Event>>>()
    val events: LiveData<Result<List<Event>>> = _events


    fun getEventsForUser(userId: Int) {
        viewModelScope.launch {
            val result = repository.getEventsForUser(userId)
            _events.value = result
            when(result){
                is Result.HttpError ->  Log.i(TAG, result.code.toString())
                is Result.NetworkError ->  Log.i(TAG, result.exception.message.toString())
                is Result.Success ->  Log.i(TAG, result.data.toString())
            }

        }
    }
}