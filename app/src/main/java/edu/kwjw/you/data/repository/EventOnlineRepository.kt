package edu.kwjw.you.data.repository

import edu.kwjw.you.data.remote.EventService
import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EventOnlineRepository @Inject constructor(private val eventService: EventService) :
    EventRepository {
    override fun getEventsForUser(userId: Int): Flow<ApiResult<List<Event>>> {
        return flow {
            emit(ApiResult.Loading)
            try {
                val result = eventService.getEventsForUser(userId).map { it.toEvent() }
                emit(ApiResult.Success(result))
            } catch (e: HttpException) {
                emit(ApiResult.HttpError(e.code()))
            } catch (e: IOException) {
                emit(ApiResult.NetworkError(e))
            }
        }

    }

    override fun addEvent(addEventDto: AddEventDto): Flow<ApiResult<Event>> {
        return flow {
            try {
                val result = eventService.addEventForUser(addEventDto).toEvent()
                emit(ApiResult.Success(result))
            } catch (e: HttpException) {
                emit(ApiResult.HttpError(e.code()))
            } catch (e: IOException) {
                emit(ApiResult.NetworkError(e))
            }
        }
    }
}