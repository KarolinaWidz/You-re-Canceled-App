package edu.kwjw.you.data.repository.network

import edu.kwjw.you.data.model.Event
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

fun interface EventService {

    @GET("/events")
    suspend fun getEventsForUser(@Query("userId") userId: Int): Response<List<Event>>

}