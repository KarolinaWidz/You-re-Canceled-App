package edu.kwjw.you.data.repository

import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.util.Result

interface EventRepository {

    suspend fun getEventsForUser(userId: Int): Result<List<Event>>

    suspend fun addEvent(addEventDto: AddEventDto):Result<Event>
}