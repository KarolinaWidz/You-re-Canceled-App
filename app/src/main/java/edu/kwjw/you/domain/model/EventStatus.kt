package edu.kwjw.you.domain.model

import edu.kwjw.you.R

enum class EventStatus(val statusId: Int) {
    ATTENDING(statusId = R.string.attending),
    CANCELLED(statusId = R.string.cancelled)
}