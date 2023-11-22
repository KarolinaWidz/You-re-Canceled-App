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

class NewEvent @Inject constructor(private val repository: EventRepository) {

    fun add(userId: Int, plainEventData: PlainEventData): Flow<ApiResult<Event>> {
        val dateTime = createDateTimeObject(plainEventData.date, plainEventData.time)
        return repository.addEvent(
            AddEventDto(userId, plainEventData.name, dateTime)
        )
    }

    fun isValid(plainEventData: PlainEventData): Boolean {
        if (isAnyBlank(plainEventData)) {
            return false
        }
        createDateTimeObject(plainEventData.date, plainEventData.time)
        return true
    }

    private fun isAnyBlank(plainEventData: PlainEventData): Boolean {
        return plainEventData.name.isBlank() || plainEventData.date.isBlank()
                || plainEventData.time.isBlank()
    }

    private fun createDateTimeObject(date: String, time: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        return LocalDateTime.parse("$date $time", formatter)
    }
}