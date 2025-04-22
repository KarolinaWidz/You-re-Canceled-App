package edu.kwjw.you.data.remote.dto

import edu.kwjw.you.data.repository.model.EventStatus
import edu.kwjw.you.data.repository.model.Participant

data class ParticipantDto(
    val userId: Int,
    val status: EventStatus,
) {
    fun toParticipant(): Participant {
        return Participant(userId = userId, status = status)
    }
}