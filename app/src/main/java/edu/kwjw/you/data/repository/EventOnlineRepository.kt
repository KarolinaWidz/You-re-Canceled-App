package edu.kwjw.you.data.repository

import edu.kwjw.you.data.remote.EventService
import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.util.Result
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EventOnlineRepository @Inject constructor(private val eventService: EventService) :
    EventRepository {
    override suspend fun getEventsForUser(userId: Int): Result<List<Event>> {
        return try {
            val result = eventService.getEventsForUser(userId).map { it.toEvent() }
            Result.Success(result)
        } catch (e: HttpException) {
            Result.HttpError(e.code())
        } catch (e: IOException) {
            Result.NetworkError(e)
        }

    }

    override suspend fun addEvent(addEventDto: AddEventDto): Result<Event> {
        return try {
            val result = eventService.addEventForUser(addEventDto).toEvent()
            Result.Success(result)
        } catch (e: HttpException) {
            Result.HttpError(e.code())
        } catch (e: IOException) {
            Result.NetworkError(e)
        }

    }
}