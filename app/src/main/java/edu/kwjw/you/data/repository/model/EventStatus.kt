package edu.kwjw.you.data.repository.model

import edu.kwjw.you.R

enum class EventStatus(val statusId: Int) {
    ATTENDING(statusId = R.string.attending),
    CANCELLED(statusId = R.string.cancelled)
}