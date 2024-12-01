package edu.kwjw.you.presentation.ui.addnewevent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.theme.AppTheme
import edu.kwjw.you.presentation.ui.theme.PaddingExtraLarge
import edu.kwjw.you.presentation.ui.theme.PaddingMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddEvent(
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
    onAddItemClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(PaddingMedium)
    ) {
        EventDetails(
            eventName = eventName,
            onEventNameChanged = onEventNameChanged,
            isEventNameError = isEventNameError,
            eventDate = eventDate,
            onEventDateChanged = onEventDateChanged,
            isEventDateError = isEventDateError,
            eventTime = eventTime,
            onEventTimeChanged = onEventTimeChanged,
            isEventTimeError = isEventTimeError,
        )
        Spacer(
            modifier = Modifier
                .padding(vertical = PaddingExtraLarge)
                .weight(1f)
        )
        AddEventButton(onAddItemClicked = onAddItemClicked)
    }
}

@Composable
private fun AddEventButton(
    modifier: Modifier = Modifier,
    onAddItemClicked: () -> Unit = {}
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onAddItemClicked
    ) {
        Text(text = stringResource(R.string.save))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@PreviewLightDark
private fun AddEventPreview() {
    AppTheme {
        AddEvent()
    }
}