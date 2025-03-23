package edu.kwjw.you.presentation.ui.eventlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.theme.AppTheme
import edu.kwjw.you.presentation.ui.theme.PaddingExtraExtraLarge
import edu.kwjw.you.presentation.ui.theme.PaddingExtraLarge
import edu.kwjw.you.presentation.ui.theme.PaddingLarge
import edu.kwjw.you.presentation.ui.theme.PaddingMedium


@Composable
internal fun EventListError(modifier: Modifier = Modifier, onRetry: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = PaddingExtraExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ErrorImage()
        OopsText()
        ErrorText()
        RetryButton(onClick = onRetry)
    }
}

@Composable
private fun ErrorImage(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.padding(horizontal = PaddingExtraLarge),
        painter = painterResource(R.drawable.drawable_error),
        contentDescription = null
    )
}

@Composable
private fun OopsText(modifier: Modifier = Modifier) {
    Text(
        stringResource(R.string.oops),
        modifier
            .fillMaxWidth()
            .padding(bottom = PaddingMedium),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
private fun ErrorText(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            stringResource(R.string.something_went_wrong),
            modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            stringResource(R.string.please_try_again),
            modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RetryButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Button(
        modifier = modifier.padding(top = PaddingLarge),
        onClick = onClick
    ) { Text(stringResource(R.string.retry)) }
}

@Composable
@Preview(showBackground = true)
private fun EventListErrorPreview() {
    AppTheme {
        EventListError()
    }
}