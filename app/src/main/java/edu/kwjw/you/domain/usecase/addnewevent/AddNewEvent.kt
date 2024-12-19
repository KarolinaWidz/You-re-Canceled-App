package edu.kwjw.you.domain.usecase.addnewevent

import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.presentation.ui.addnewevent.Time
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

class AddNewEvent @Inject constructor(private val repository: EventRepository) {
    fun execute(
        userId: Int,
        dateTimestamp: Long,
        time: Time,
        name: String
    ): Flow<ApiResult<Event>> {
        val dateTime = createDateTimeObject(dateTimestamp = dateTimestamp, time = time)
        return repository.addEvent(AddEventDto(userId, name, dateTime))
    }

    private fun createDateTimeObject(dateTimestamp: Long, time: Time): LocalDateTime {
        val zoneId = ZoneId.systemDefault()
        val date = Instant.ofEpochMilli(dateTimestamp).atZone(zoneId).toLocalDate()
        val time = LocalTime.of(time.hour, time.minute)
        return LocalDateTime.of(date, time)
    }
}