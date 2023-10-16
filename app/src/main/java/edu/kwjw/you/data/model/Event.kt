package edu.kwjw.you.data.model

import com.squareup.moshi.Json
import edu.kwjw.you.EventStatus
import java.util.UUID

data class Event(
    val id: UUID,
    val userId: Int,
    val name: String,
    val date: String,
    val participants: List<Participant>,
    val status: EventStatus,
    @Json(name = "cancelled") val isCancelled: Boolean
)
