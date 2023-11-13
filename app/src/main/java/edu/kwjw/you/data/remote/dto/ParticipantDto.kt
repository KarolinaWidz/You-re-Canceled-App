package edu.kwjw.you.data.remote.dto

import edu.kwjw.you.domain.model.EventStatus
import edu.kwjw.you.domain.model.Participant

data class ParticipantDto(
    val userId: Int,
    val status: EventStatus,
) {
    fun toParticipant(): Participant {
        return Participant(userId = userId, status = status)
    }
}