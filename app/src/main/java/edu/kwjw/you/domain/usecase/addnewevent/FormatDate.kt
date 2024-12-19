package edu.kwjw.you.domain.usecase.addnewevent

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FormatDate @Inject constructor() {
    internal fun execute(timestamp: Long?): String {
        if (timestamp == null) {
            return ""
        }
        val zoneId = ZoneId.systemDefault()
        val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId)
        return localDateTime.format(DateTimeFormatter.ofPattern(FULL_DATE_PATTERN))
    }

    private companion object {
        const val FULL_DATE_PATTERN = "EEEE, dd MMM yyyy"
    }
}