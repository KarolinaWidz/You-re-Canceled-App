package edu.kwjw.you.presentation.ui.addnewevent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.kwjw.you.R

@Composable
internal fun EventDetails(
    modifier: Modifier = Modifier,
    eventName: String = "",
    onEventNameChanged: (String) -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth()) {
        EventDetailsTitle()
        EventNameField(name = eventName, onNameChanged = onEventNameChanged)
    }
}

@Composable
private fun EventDetailsTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.event_details)
    )
}

@Composable
private fun EventNameField(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = name,
        onValueChange = onNameChanged,
        label = { Text("Event name") }
    )
}

@Composable
@Preview(showBackground = true)
private fun EventDetailsPreview() {
    EventDetails()
}