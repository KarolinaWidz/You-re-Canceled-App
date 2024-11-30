package edu.kwjw.you.presentation.ui.util

import android.os.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
class FutureDates : SelectableDates {
    private val currentDate = LocalDate.now()


    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            utcTimeMillis > currentDate.toEpochSecond(LocalTime.of(0, 0, 0), ZoneOffset.UTC) * 1000
        } else {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
            utcTimeMillis > calendar.timeInMillis
        }
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year >= currentDate.year
    }

    companion object{
        private const val UTC = "UTC"
    }
}