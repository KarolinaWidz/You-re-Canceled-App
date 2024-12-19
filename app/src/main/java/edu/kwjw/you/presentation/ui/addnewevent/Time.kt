package edu.kwjw.you.presentation.ui.addnewevent

import androidx.annotation.IntRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState

data class Time(
    @get:IntRange(from = 0, to = 59) val minute: Int,
    @get:IntRange(from = 0, to = 23) val hour: Int,
)

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.toTime() =
    Time(
        minute = this.minute,
        hour = this.hour,
    )