package edu.kwjw.you.data.remote

import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.data.remote.dto.EventDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EventService {

    @GET("/events")
    suspend fun getEventsForUser(@Query("userId") userId: Int): List<EventDto>

    @POST("/events")
    suspend fun addEventForUser(@Body addEventDto: AddEventDto): EventDto

}