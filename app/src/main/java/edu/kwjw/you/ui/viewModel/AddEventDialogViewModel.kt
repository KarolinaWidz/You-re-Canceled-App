package edu.kwjw.you.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.data.model.Event
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.data.repository.network.request.AddEventRequest
import edu.kwjw.you.util.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventDialogViewModel @Inject constructor(private val repository: EventRepository) :
    ViewModel() {
    private val _addEventResult = MutableLiveData<Result<Event>>()

    fun addNewEvent(addEventRequest: AddEventRequest) {
        viewModelScope.launch {
            _addEventResult.value = repository.addEvent(addEventRequest)
            Log.i("AddEventDialogViewModel", _addEventResult.value.toString())
        }
    }
}