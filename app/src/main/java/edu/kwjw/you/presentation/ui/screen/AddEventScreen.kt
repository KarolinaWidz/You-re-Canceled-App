package edu.kwjw.you.presentation.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.addnewevent.AddEvent
import edu.kwjw.you.presentation.ui.common.TopTitleBarWithBackButton
import edu.kwjw.you.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventEventScreen(modifier: Modifier = Modifier) {

    val title = stringResource(R.string.add_new_event)
    val snackbarHost = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        topBar = { TopTitleBarWithBackButton(title = title) },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        snackbarHost = { SnackbarHost(hostState = snackbarHost) }
    ) { contentPadding ->
        AddEvent(
            modifier = Modifier.padding(contentPadding),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@PreviewLightDark
private fun EventEventScreenPreview() {
    AppTheme {
        EventEventScreen()
    }
}