package edu.kwjw.you.data.repository

import edu.kwjw.you.data.model.Event
import edu.kwjw.you.util.Result

fun interface EventRepository {

    suspend fun getEventsForUser(userId: Int): Result<List<Event>>
}