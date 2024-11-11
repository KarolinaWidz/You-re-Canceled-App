package edu.kwjw.you.presentation.ui.eventlist

import edu.kwjw.you.domain.model.EventStatus
import java.time.LocalDateTime

data class EventItem(
    val name: String,
    val date: LocalDateTime,
    val status: EventStatus,
    val imageUrl: String? = null,
)