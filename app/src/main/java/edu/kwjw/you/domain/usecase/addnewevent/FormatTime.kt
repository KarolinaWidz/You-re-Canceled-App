package edu.kwjw.you.domain.usecase.addnewevent

import edu.kwjw.you.presentation.ui.addnewevent.Time
import javax.inject.Inject

class FormatTime @Inject constructor() {
    internal fun execute(time: Time?): String {
        if (time == null) {
            return ""
        }

        return TIME_PATTERN.format(time.hour, time.minute)
    }

    private companion object {
        const val TIME_PATTERN = "%02d:%02d"
    }
}