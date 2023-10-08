package edu.kwjw.you.data.repository.network

import edu.kwjw.you.data.model.Event
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.util.Result
import java.io.IOException
import javax.inject.Inject

class EventOnlineRepository @Inject constructor(private val eventService: EventService) :
    EventRepository {
    override suspend fun getEventsForUser(userId: Int): Result<List<Event>> {
        return try {
            val result = eventService.getEventsForUser(userId)
            if (result.isSuccessful) {
                Result.Success(result.body()!!)
            } else {
                Result.HttpError(result.code())
            }
        } catch (e: IOException) {
            Result.NetworkError(e)
        }

    }
}