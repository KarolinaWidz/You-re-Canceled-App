package edu.kwjw.you.util

import edu.kwjw.you.util.LocalDateTimeExtensionConst.FULL_DATE_PATTERN
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toFullDateString(): String = this.format(DateTimeFormatter.ofPattern(FULL_DATE_PATTERN))

private object LocalDateTimeExtensionConst {
    const val FULL_DATE_PATTERN = "EEEE, dd MMM YYYY"
}