package edu.kwjw.you.domain.usecase

import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.domain.model.PlainEventData
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AddNewEvent @Inject constructor(private val repository: EventRepository) {

    operator fun invoke(userId: Int, plainEventData: PlainEventData): Flow<ApiResult<Event>> {
        return repository.addEvent(
            AddEventDto(
                userId, plainEventData.name, LocalDateTime.now()
            )
        )
    }
//todo: finish
    fun isEventDataValid(plainEventData: PlainEventData): Boolean {
        with(plainEventData) {
            return name.isNotBlank() && date.isNotBlank() && time.isNotBlank()
        }
    }

    private fun createDateTimeObject(date: String, time: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        return LocalDateTime.parse("$date $time", formatter)
    }
}