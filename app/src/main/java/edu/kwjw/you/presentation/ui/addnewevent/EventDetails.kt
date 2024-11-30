package edu.kwjw.you.presentation.ui.addnewevent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.common.ClickableTextField
import edu.kwjw.you.presentation.ui.common.DatePickerModalDialog
import edu.kwjw.you.presentation.ui.common.TimePickerModalDialog
import edu.kwjw.you.presentation.ui.theme.PaddingMedium
import edu.kwjw.you.presentation.ui.theme.PaddingSmall

@Composable
internal fun EventDetails(
    modifier: Modifier = Modifier,
    eventName: String = "",
    onEventNameChanged: (String) -> Unit = {},
    eventDate: String = "",
    onEventDateChanged: (String) -> Unit = {},
    eventTime: String = "",
    onEventTimeChanged: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(PaddingSmall)
    ) {
        EventDetailsTitle()
        EventNameField(name = eventName, onNameChanged = onEventNameChanged)
        EventDate(date = eventDate, onDateChanged = onEventDateChanged)
        EventTime(time = eventTime, onTimeChanged = onEventTimeChanged)
    }
}

@Composable
private fun EventDetailsTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(bottom = PaddingMedium),
        text = stringResource(R.string.event_details),
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun EventNameField(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (String) -> Unit,
    isError: Boolean = false
) {
    OutlinedTextField(
        modifier = modifier,
        value = name,
        onValueChange = onNameChanged,
        label = { Text(stringResource(R.string.event_name)) },
        isError = isError,
        supportingText = {
            if (isError) {
                ShowError(R.string.event_name_value_is_required_please_provide_a_valid_event_name)
            }
        }
    )
}

@Composable
private fun EventDate(
    modifier: Modifier = Modifier,
    date: String,
    onDateChanged: (String) -> Unit,
    isError: Boolean = false,
) {

    ClickableTextField(
        modifier = modifier,
        value = date,
        onValueChange = onDateChanged,
        label = { Text(stringResource(R.string.event_date)) },
        isError = isError,
        supportingText = {
            if (isError) {
                ShowError(R.string.event_date_value_is_required_please_provide_a_valid_event_date)
            }
        },
        onClicked = { DatePickerModalDialog() }
    )
}

@Composable
private fun EventTime(
    modifier: Modifier = Modifier,
    time: String,
    onTimeChanged: (String) -> Unit,
    isError: Boolean = false,
) {

    ClickableTextField(
        modifier = modifier,
        value = time,
        onValueChange = onTimeChanged,
        label = { Text(stringResource(R.string.event_time)) },
        isError = isError,
        supportingText = {
            if (isError) {
                ShowError(R.string.event_time_value_is_required_please_provide_a_valid_event_time)
            }
        },
        onClicked = { TimePickerModalDialog() }
    )
}

@Composable
private fun ShowError(stringId: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier.padding(end = PaddingSmall),
            imageVector = Icons.Filled.Info,
            tint = MaterialTheme.colorScheme.error,
            contentDescription = null
        )
        Text(text = stringResource(stringId), color = MaterialTheme.colorScheme.error)

    }
}

@Composable
@Preview(showBackground = true)
private fun EventDetailsPreview() {
    EventDetails()
}