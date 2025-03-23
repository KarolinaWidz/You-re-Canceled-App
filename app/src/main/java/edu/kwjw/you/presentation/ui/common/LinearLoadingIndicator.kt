package edu.kwjw.you.presentation.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.kwjw.you.presentation.ui.theme.AppTheme
import edu.kwjw.you.presentation.ui.theme.ThicknessSmall


@Composable
internal fun LinearLoadingIndicator(modifier: Modifier = Modifier) {
    LinearProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .height(ThicknessSmall),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
}


@Composable
@Preview(showBackground = true)
private fun TimePickerDialogPreview() {
    AppTheme {
        LinearLoadingIndicator()
    }
}