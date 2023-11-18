package edu.kwjw.you.data.remote.adapter

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.UUID

class UuidAdapterTest {

    private val uuidAdapter = UuidAdapter()

    @Test
    fun `should parse uuid to json successfully`() {
        val uuid = UUID.fromString("a885eca0-8623-11ee-b9d1-0242ac120002")
        val expected = "a885eca0-8623-11ee-b9d1-0242ac120002"
        assertEquals(expected, uuidAdapter.uuidToJson(uuid))
    }

    @Test
    fun `should parse json to uuid successfully`() {
        val json = "b03ddcf0-8623-11ee-b9d1-0242ac120002"
        val expected = UUID.fromString("b03ddcf0-8623-11ee-b9d1-0242ac120002")
        assertEquals(expected, uuidAdapter.jsonToUuid(json))
    }
}