package edu.kwjw.you.data.remote.dto

import java.time.LocalDateTime

data class AddEventDto(val userId: Int, val name: String, val datetime: LocalDateTime)