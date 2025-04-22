package edu.kwjw.you.data.repository

import edu.kwjw.you.data.repository.model.Event
import edu.kwjw.you.presentation.ui.addnewevent.Time
import edu.kwjw.you.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getEventsForUser(userId: Int): Flow<ApiResult<List<Event>>>

    fun addEvent(
        userId: Int,
        dateTimestamp: Long,
        time: Time,
        name: String
    ): Flow<ApiResult<Event>>
}