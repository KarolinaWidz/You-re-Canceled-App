package edu.kwjw.you.domain.usecase

import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventsForUser @Inject constructor(private val repository: EventRepository) {
    operator fun invoke(userId: Int): Flow<ApiResult<List<Event>>> {
        return repository.getEventsForUser(userId)
    }
}
