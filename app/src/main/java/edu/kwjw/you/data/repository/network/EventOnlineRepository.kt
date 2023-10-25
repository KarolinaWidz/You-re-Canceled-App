package edu.kwjw.you.data.repository.network

import edu.kwjw.you.data.model.Event
import edu.kwjw.you.data.repository.EventRepository
import edu.kwjw.you.data.repository.network.request.AddEventRequest
import edu.kwjw.you.util.Result
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class EventOnlineRepository @Inject constructor(private val eventService: EventService) :
    EventRepository {
    override suspend fun getEventsForUser(userId: Int): Result<List<Event>> {
        return try {
            val result = eventService.getEventsForUser(userId)
            checkResultStatus(result)
        } catch (e: IOException) {
            Result.NetworkError(e)
        }

    }

    override suspend fun addEvent(addEventRequest: AddEventRequest): Result<Event> {
        return try {
            val result = eventService.addEventForUser(addEventRequest)
            checkResultStatus(result)
        } catch (e: IOException) {
            Result.NetworkError(e)
        }
    }

    private fun <T> checkResultStatus(result: Response<T>): Result<T> {
        return if (result.isSuccessful) Result.Success(result.body()!!) else Result.HttpError(result.code())
    }
}