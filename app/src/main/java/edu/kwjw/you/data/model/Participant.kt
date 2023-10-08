package edu.kwjw.you.data.model

import com.squareup.moshi.Json
import edu.kwjw.you.EventStatus

data class Participant(
    val userId: Int,
    val status: EventStatus,
    @Json(name = "cancelled") val isCancelled: Boolean
)