package edu.kwjw.you.data.remote.dto

import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.domain.model.EventStatus
import java.time.LocalDateTime
import java.util.UUID

data class EventDto(
    val id: UUID,
    val authorId: String,
    val status: EventStatus,
    val date: LocalDateTime,
    val name: String,
    val participants: List<ParticipantDto>
) {
    fun toEvent(): Event {
        return Event(
            id = id,
            userId = authorId,
            name = name,
            date = date,
            participants = participants.map { it.toParticipant() },
            status = status
        )
    }
}
