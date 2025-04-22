package edu.kwjw.you.presentation.ui.eventlist

import edu.kwjw.you.data.repository.model.Event
import edu.kwjw.you.data.repository.model.EventStatus
import kotlinx.collections.immutable.toPersistentList
import java.time.LocalDateTime

data class EventItem(
    val name: String,
    val date: LocalDateTime,
    val status: EventStatus,
    val imageUrl: String? = null,
)

fun Event.toEventItem(): EventItem = EventItem(
    name = name,
    date = date,
    status = status,
    imageUrl = null
)

fun List<Event>.toEventItemList() = this.map { event -> event.toEventItem() }.toPersistentList()
