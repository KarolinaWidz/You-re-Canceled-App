package edu.kwjw.you.data.repository

import edu.kwjw.you.data.model.Event
import edu.kwjw.you.data.repository.network.request.AddEventRequest
import edu.kwjw.you.util.Result

interface EventRepository {

    suspend fun getEventsForUser(userId: Int): Result<List<Event>>

    suspend fun addEvent(addEventRequest: AddEventRequest):Result<Event>
}