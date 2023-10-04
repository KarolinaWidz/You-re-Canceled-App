package edu.kwjw.you.ui.recyclerView.model

import edu.kwjw.you.EventStatus
import java.util.UUID

data class Event(val id:UUID, val name:String, val status: EventStatus)
