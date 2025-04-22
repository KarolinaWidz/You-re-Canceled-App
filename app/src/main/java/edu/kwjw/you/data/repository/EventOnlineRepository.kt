package edu.kwjw.you.data.repository

import android.util.Log
import edu.kwjw.you.data.remote.EventService
import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.data.repository.model.Event
import edu.kwjw.you.presentation.ui.addnewevent.Time
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
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
    override fun getEventsForUser(userId: Int): Flow<ApiResult<List<Event>>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                val result = eventService.getEventsForUser(userId).map { it.toEvent() }
                emit(ApiResult.Success(result))
                Log.i(LOG_TAG, "Events fetched successfully: $result")
            } catch (e: HttpException) {
                emit(ApiResult.HttpError(e.code()))
                Log.e(LOG_TAG, "Events fetching failure because of ${e.code()}")
            } catch (e: IOException) {
                emit(ApiResult.NetworkError(e))
                Log.e(LOG_TAG, "Events fetching failure because of network error: $e")
            }
        }.debounce(TIME_PERIOD)
    }

    override fun addEvent(
        userId: Int,
        dateTimestamp: Long,
        time: Time,
        name: String
    ): Flow<ApiResult<Event>> {
        val dateTime = createDateTimeObject(dateTimestamp = dateTimestamp, time = time)
        return flow {
            try {
                val result =
                    eventService.addEventForUser(AddEventDto(userId, name, dateTime)).toEvent()
                emit(ApiResult.Success(result))
                Log.i(LOG_TAG, "Event saved successfully: $result")
            } catch (e: HttpException) {
                emit(ApiResult.HttpError(e.code()))
                Log.e(LOG_TAG, "Event saving failure because of ${e.code()}")
            } catch (e: IOException) {
                emit(ApiResult.NetworkError(e))
                Log.e(LOG_TAG, "Event saving failure because of network error: $e")
            }
        }
    }

    private fun createDateTimeObject(dateTimestamp: Long, time: Time): LocalDateTime {
        val zoneId = ZoneId.systemDefault()
        val date = Instant.ofEpochMilli(dateTimestamp).atZone(zoneId).toLocalDate()
        val time = LocalTime.of(time.hour, time.minute)
        return LocalDateTime.of(date, time)
    }

    companion object {
        const val LOG_TAG = "EventOnlineRepository"
        const val TIME_PERIOD = 1000L
    }
}