package edu.kwjw.you.data.repository.model

import java.time.LocalDateTime
import java.util.UUID

data class Event(
    val id: UUID,
    val userId: String,
    val name: String,
    val date: LocalDateTime,
    val participants: List<Participant>,
    val status: EventStatus,
)
