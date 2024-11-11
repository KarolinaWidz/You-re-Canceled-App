package edu.kwjw.you.presentation.ui.eventlist

import edu.kwjw.you.domain.model.EventStatus
import java.time.LocalDateTime

data class EventItem(
    val name: String = "Designer Meetup 2022",
    val date: LocalDateTime = LocalDateTime.now(),
    val status: EventStatus = EventStatus.ATTENDING,
    val imageUrl: String? = "https://upload.wikimedia.org/wikipedia/commons/e/e5/Dogge_Odin.jpg",
)