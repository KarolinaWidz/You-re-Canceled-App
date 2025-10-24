package edu.kwjw.you.data.remote

import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.data.remote.dto.EventDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventService {

    @GET("/events")
    suspend fun getEventsForLoggedInUser(): List<EventDto>

    @POST("/events")
    suspend fun addEventForUser(@Body addEventDto: AddEventDto): EventDto

    @GET("/events/{id}")
    suspend fun getEventById(@Path("id") id: String): EventDto
}