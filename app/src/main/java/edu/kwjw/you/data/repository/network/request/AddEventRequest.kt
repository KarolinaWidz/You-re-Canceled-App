package edu.kwjw.you.data.repository.network.request

import java.time.LocalDateTime

data class AddEventRequest(val userId: Int, val name: String, val datetime: LocalDateTime)