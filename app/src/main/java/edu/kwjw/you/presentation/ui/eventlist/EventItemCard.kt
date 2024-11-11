package edu.kwjw.you.presentation.ui.eventlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import edu.kwjw.you.R
import edu.kwjw.you.domain.model.EventStatus
import edu.kwjw.you.presentation.ui.theme.CornerRadiusMedium
import edu.kwjw.you.presentation.ui.theme.PaddingMedium
import edu.kwjw.you.presentation.ui.theme.PaddingSmall
import edu.kwjw.you.presentation.ui.theme.SizeMedium
import edu.kwjw.you.util.toFullDateString
import java.time.LocalDateTime

@Composable
internal fun EventCard(eventItem: EventItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(PaddingSmall)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {

            EventImage(imageUrl = eventItem.imageUrl)

            Column(modifier = Modifier.weight(2f)) {
                EventName(name = eventItem.name)
                EventDate(date = eventItem.date)
            }

            VerticalDivider(modifier = Modifier.padding(vertical = PaddingMedium))
            EventStatusText(
                status = eventItem.status
            )
        }
    }
}

@Composable
private fun EventImage(imageUrl: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        modifier = modifier
            .padding(PaddingMedium)
            .size(SizeMedium)
            .clip(RoundedCornerShape(CornerRadiusMedium)),
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(imageUrl)
            .build(),
        fallback = painterResource(R.drawable.ic_default_event),
        placeholder = painterResource(R.drawable.ic_default_event),
        error = painterResource(R.drawable.ic_default_event),
        contentDescription = stringResource(R.string.event_image),
    )
}

@Composable
private fun EventName(name: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(bottom = PaddingMedium),
        text = name,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
private fun EventDate(date: LocalDateTime, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = date.toFullDateString(),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun EventStatusText(status: EventStatus, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(PaddingMedium),
        text = stringResource(status.statusId),
        fontWeight = FontWeight.Medium,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
private fun EventCardPreview() {
    EventCard(
        eventItem = EventItem(
            name = "Designer meeting",
            date = LocalDateTime.now(),
            status = EventStatus.ATTENDING
        )
    )
}