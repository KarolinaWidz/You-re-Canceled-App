package edu.kwjw.you.presentation.util

import edu.kwjw.you.presentation.ui.addnewevent.Time
import edu.kwjw.you.presentation.util.DateTimeExtensionConst.FULL_DATE_PATTERN
import edu.kwjw.you.presentation.util.DateTimeExtensionConst.TIME_PATTERN
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDateTime.toFullDateString(): String =
    this.format(DateTimeFormatter.ofPattern(FULL_DATE_PATTERN))

fun Long.toFullDateString(): String {
    val zoneId = ZoneId.systemDefault()
    val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zoneId)
    return localDateTime.format(DateTimeFormatter.ofPattern(FULL_DATE_PATTERN))
}

fun Time.toTimeString(): String = TIME_PATTERN.format(this.hour, this.minute)

private object DateTimeExtensionConst {
    const val FULL_DATE_PATTERN = "EEEE, dd MMM yyyy"
    const val TIME_PATTERN = "%02d:%02d"
}

