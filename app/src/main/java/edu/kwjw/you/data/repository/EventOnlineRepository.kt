package edu.kwjw.you.data.repository

import android.util.Log
import edu.kwjw.you.data.remote.EventService
import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.data.repository.model.Event
import edu.kwjw.you.presentation.ui.addnewevent.Time
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.FlowPreview
import retrofit2.HttpException
import java.io.IOException
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

class EventOnlineRepository @Inject constructor(private val eventService: EventService) :
    EventRepository {

    @OptIn(FlowPreview::class)
    override suspend fun getEventsForUser(userId: String): ApiResult<List<Event>> = try {
        val result = eventService.getEventsForUser(userId).map { it.toEvent() }
        Log.i(LOG_TAG, "Events fetched successfully: $result")
        ApiResult.Success(result)

    } catch (e: HttpException) {
        Log.e(LOG_TAG, "Events fetching failure because of ${e.code()}")
        ApiResult.HttpError(e.code())

    } catch (e: IOException) {
        Log.e(LOG_TAG, "Events fetching failure because of network error: $e")
        ApiResult.NetworkError(e)
    }

    override suspend fun addEvent(
        userId: String, dateTimestamp: Long, time: Time, name: String
    ): ApiResult<Event> = try {
        val dateTime = createDateTimeObject(dateTimestamp = dateTimestamp, time = time)
        val result = eventService.addEventForUser(AddEventDto(userId, name, dateTime)).toEvent()
        Log.i(LOG_TAG, "Event saved successfully: $result")
        ApiResult.Success(result)

    } catch (e: HttpException) {
        Log.e(LOG_TAG, "Event saving failure because of ${e.code()}")
        ApiResult.HttpError(e.code())

    } catch (e: IOException) {
        Log.e(LOG_TAG, "Event saving failure because of network error: $e")
        ApiResult.NetworkError(e)
    }

    private fun createDateTimeObject(dateTimestamp: Long, time: Time): LocalDateTime {
        val zoneId = ZoneId.systemDefault()
        val date = Instant.ofEpochMilli(dateTimestamp).atZone(zoneId).toLocalDate()
        val time = LocalTime.of(time.hour, time.minute)
        return LocalDateTime.of(date, time)
    }

    companion object {
        const val LOG_TAG = "EventOnlineRepository"
    }
}