package edu.kwjw.you.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.util.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventDialogViewModel @Inject constructor(private val repository: EventRepository) :
    ViewModel() {
    private val _addEventResult = MutableLiveData<Result<Event>>()
    val addEventResult: LiveData<Result<Event>> get() = _addEventResult

    fun addNewEvent(addEventDto: AddEventDto) {
        viewModelScope.launch {
            _addEventResult.value = repository.addEvent(addEventDto)
        }
    }
}