package edu.kwjw.you.presentation.ui.common

import android.icu.util.Calendar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimePickerModalDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onTimeSelected: (TimePickerState) -> Unit = {}
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime[Calendar.HOUR_OF_DAY],
        initialMinute = currentTime[Calendar.MINUTE],
        is24Hour = true
    )

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.dismiss))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onTimeSelected(timePickerState)
                onDismiss
            }) {
                Text(stringResource(R.string.ok))
            }
        },

        text = {
            TimePicker(state = timePickerState)
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun TimePickerDialogPreview() {
    AppTheme {
        TimePickerModalDialog()
    }
}