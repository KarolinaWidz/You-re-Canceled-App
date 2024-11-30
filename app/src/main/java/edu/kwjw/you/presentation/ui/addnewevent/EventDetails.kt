package edu.kwjw.you.presentation.ui.addnewevent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.common.ClickableTextField
import edu.kwjw.you.presentation.ui.common.DatePickerModalDialog
import edu.kwjw.you.presentation.ui.common.TimePickerModalDialog
import edu.kwjw.you.presentation.ui.theme.PaddingMedium
import edu.kwjw.you.presentation.ui.theme.PaddingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventDetails(
    modifier: Modifier = Modifier,
    eventName: String = "",
    onEventNameChanged: (String) -> Unit = {},
    isEventNameError: Boolean = false,
    eventDate: String = "",
    onEventDateChanged: (Long?) -> Unit = {},
    isEventDateError: Boolean = false,
    eventTime: String = "",
    onEventTimeChanged: (TimePickerState) -> Unit = {},
    isEventTimeError: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(PaddingMedium)
    ) {
        EventDetailsTitle()
        EventNameField(
            name = eventName,
            onNameChanged = onEventNameChanged,
            isError = isEventNameError
        )
        EventDate(date = eventDate, onDateChanged = onEventDateChanged, isError = isEventDateError)
        EventTime(time = eventTime, onTimeChanged = onEventTimeChanged, isError = isEventTimeError)
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
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = PaddingSmall),
        value = name,
        onValueChange = onNameChanged,

        label = { Text(stringResource(R.string.event_name)) },
        isError = isError,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_event_name),
                contentDescription = null
            )
        },
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
    onDateChanged: (Long?) -> Unit,
    isError: Boolean = false,
) {
    var isClicked by rememberSaveable { mutableStateOf(false) }
    ClickableTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = PaddingSmall),
        value = date,
        onValueChange = {},
        label = { Text(stringResource(R.string.event_date)) },
        isError = isError,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_event_date),
                contentDescription = null
            )
        },
        supportingText = {
            if (isError) {
                ShowError(R.string.event_date_value_is_required_please_provide_a_valid_event_date)
            }
        },
        onClicked = {
            DatePickerModalDialog(
                onDismiss = { isClicked = false },
                onDateSelected = {
                    onDateChanged
                    isClicked = false
                }
            )
        },
        isClicked = isClicked,
        onIsClickedChanged = { isClicked = !isClicked }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventTime(
    modifier: Modifier = Modifier,
    time: String,
    onTimeChanged: (TimePickerState) -> Unit,
    isError: Boolean = false,
) {
    var isClicked by rememberSaveable { mutableStateOf(false) }
    ClickableTextField(
        modifier = modifier.fillMaxWidth(),
        value = time,
        onValueChange = {},
        label = { Text(stringResource(R.string.event_time)) },
        isError = isError,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_event_time),
                contentDescription = null
            )
        },
        supportingText = {
            if (isError) {
                ShowError(R.string.event_time_value_is_required_please_provide_a_valid_event_time)
            }
        },
        onClicked = {
            TimePickerModalDialog(onTimeSelected = {
                onTimeChanged
                isClicked = false
            })
        },
        isClicked = isClicked,
        onIsClickedChanged = { isClicked = !isClicked }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun EventDetailsPreview() {
    EventDetails()
}