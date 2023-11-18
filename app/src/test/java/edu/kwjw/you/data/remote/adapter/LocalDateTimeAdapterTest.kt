package edu.kwjw.you.data.remote.adapter

import com.squareup.moshi.JsonDataException
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class LocalDateTimeAdapterTest {

    private val localDateTimeAdapter = LocalDateTimeAdapter()

    @Test
    fun `should parse date to string successfully`() {
        val dateTime = LocalDateTime.of(2011, 12, 3, 10, 15, 30)
        val expected = "2011-12-03T10:15:30"
        assertEquals(expected, localDateTimeAdapter.localDateTimeToJson(dateTime))
    }

    @Test
    fun `should parse string to date successfully`() {
        val date = "0000-12-03T10:15:30"
        val expected = LocalDateTime.of(0, 12, 3, 10, 15, 30)
        assertEquals(expected, localDateTimeAdapter.jsonToLocalDateTime(date))
    }

    @Test
    fun `should throw exception after wrong date`() {
        val date = "0"
        val exception =
            assertThrows<JsonDataException> { localDateTimeAdapter.jsonToLocalDateTime(date) }
        val expectedMessage = "Cannot parse date: $date"
        assertEquals(expectedMessage, exception.message)
    }
}