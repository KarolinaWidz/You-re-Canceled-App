package edu.kwjw.you.presentation.ui.eventlist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun EventList(modifier: Modifier = Modifier,
                       events: ImmutableList<EventItem> = persistentListOf(EventItem())
){
    LazyColumn(modifier = modifier){
        items(events){ event ->
            EventCard(eventItem = event)
        }
    }
}

@Preview
@Composable
private fun EventCardPreview() {
    EventList()
}