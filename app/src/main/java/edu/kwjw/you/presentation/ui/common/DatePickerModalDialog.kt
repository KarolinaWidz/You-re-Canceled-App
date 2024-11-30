package edu.kwjw.you.presentation.ui.common

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.util.FutureDates
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DatePickerModalDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    Calendar.getInstance()
    val datePickerState = rememberDatePickerState(
        selectableDates = FutureDates()
    )

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.dismiss))
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.ok))
            }
        },
    ) {
        DatePicker(state = datePickerState, showModeToggle = false)
    }
}

@Composable
@Preview(showBackground = true)
private fun DatePickerDialogPreview() {
    DatePickerModalDialog()
}