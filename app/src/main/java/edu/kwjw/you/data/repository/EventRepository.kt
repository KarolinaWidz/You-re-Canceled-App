package edu.kwjw.you.data.repository

import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getEventsForUser(userId: Int): Flow<ApiResult<List<Event>>>

    fun addEvent(addEventDto: AddEventDto): Flow<ApiResult<Event>>
}