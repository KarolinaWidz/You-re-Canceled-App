package edu.kwjw.you.data.repository.network

import edu.kwjw.you.data.model.Event
import edu.kwjw.you.data.repository.network.request.AddEventRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EventService {

    @GET("/events")
    suspend fun getEventsForUser(@Query("userId") userId: Int): Response<List<Event>>

    @POST("/events")
    suspend fun addEventForUser(@Body addEventRequest: AddEventRequest):Response<Event>

}