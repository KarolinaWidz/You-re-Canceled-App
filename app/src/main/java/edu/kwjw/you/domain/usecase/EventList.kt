package edu.kwjw.you.domain.usecase

import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EventList @Inject constructor(private val repository: EventRepository) {
    fun getEventsForUser(userId: Int): Flow<Result<List<Event>>> {
        return flow { emit(repository.getEventsForUser(userId)) }
    }
}
