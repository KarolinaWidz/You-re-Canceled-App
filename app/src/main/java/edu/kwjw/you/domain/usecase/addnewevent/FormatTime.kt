package edu.kwjw.you.domain.usecase.addnewevent

import android.util.Log
import edu.kwjw.you.presentation.ui.addnewevent.Time
import javax.inject.Inject

class FormatTime @Inject constructor() {
    internal fun execute(time: Time?): String {
        if (time == null) {
            Log.w(LOG_TAG, "Timestamp is null")
            return ""
        }

        return TIME_PATTERN.format(time.hour, time.minute)
    }

    private companion object {
        const val LOG_TAG = "FormatTime"
        const val TIME_PATTERN = "%02d:%02d"
    }
}