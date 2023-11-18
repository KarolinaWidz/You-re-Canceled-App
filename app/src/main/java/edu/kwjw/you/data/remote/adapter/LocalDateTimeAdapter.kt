package edu.kwjw.you.data.remote.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson
import java.time.DateTimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter {
    @ToJson
    fun localDateTimeToJson(localDateTime: LocalDateTime): String {
        try {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            return formatter.format(localDateTime)
        } catch (e: DateTimeException) {
            throw JsonDataException("Cannot parse date: $localDateTime")
        }

    }

    @FromJson
    fun jsonToLocalDateTime(json: String): LocalDateTime {
        try {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            return LocalDateTime.parse(json, formatter)
        } catch (e: DateTimeException) {
            throw JsonDataException("Cannot parse date: $json")
        }
    }
}