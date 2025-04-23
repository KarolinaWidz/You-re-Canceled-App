package edu.kwjw.you.data.repository

import edu.kwjw.you.data.repository.model.Event
import edu.kwjw.you.presentation.ui.addnewevent.Time
import edu.kwjw.you.util.ApiResult

interface EventRepository {

    suspend fun getEventsForUser(userId: Int): ApiResult<List<Event>>

    suspend fun addEvent(
        userId: Int,
        dateTimestamp: Long,
        time: Time,
        name: String
    ): ApiResult<Event>
}