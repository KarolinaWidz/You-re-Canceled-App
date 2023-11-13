package edu.kwjw.you.data.remote.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.UUID

class UuidAdapter {

    @ToJson
    fun uuidToJson(uuid: UUID): String {
        return uuid.toString()
    }

    @FromJson
    fun jsonToUuid(json: String): UUID {
        return UUID.fromString(json)
    }
}