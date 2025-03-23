package edu.kwjw.you.presentation.ui.eventlist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.kwjw.you.domain.model.EventStatus
import edu.kwjw.you.presentation.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDateTime

@Composable
internal fun EventList(
    modifier: Modifier = Modifier,
    events: ImmutableList<EventItem> = persistentListOf()
) {
    LazyColumn(modifier = modifier) {
        items(events) { event ->
            EventCard(eventItem = event)
        }
    }
}

@Preview
@Composable
private fun EventCardPreview() {
    AppTheme {
        EventList(
            events = persistentListOf(
                EventItem(
                    name = "Designer meeting",
                    date = LocalDateTime.now(),
                    status = EventStatus.ATTENDING
                ), EventItem(
                    name = "Designer meeting 2",
                    date = LocalDateTime.now(),
                    status = EventStatus.ATTENDING
                )
            )
        )
    }
}