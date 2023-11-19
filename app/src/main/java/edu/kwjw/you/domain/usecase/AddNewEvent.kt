package edu.kwjw.you.domain.usecase

import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class AddNewEvent @Inject constructor(private val repository: EventRepository) {
    operator fun invoke(userId: Int, name: String): Flow<ApiResult<Event>> {
        return repository.addEvent(
            AddEventDto(
                userId, name, LocalDateTime.now()
            )
        )
    }
}