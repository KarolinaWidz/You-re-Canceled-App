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
import androidx.compose.ui.tooling.preview.Preview
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.common.BottomBar
import edu.kwjw.you.presentation.ui.common.TopTitleBar
import edu.kwjw.you.presentation.ui.eventlist.EventList
import edu.kwjw.you.presentation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventListScreen(modifier: Modifier = Modifier) {

    val title = stringResource(R.string.app_name)
    val snackbarHost = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier,
        topBar = { TopTitleBar(title = title) },
        bottomBar = { BottomBar() },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        snackbarHost = { SnackbarHost(hostState = snackbarHost) }
    ) { contentPadding ->
        EventList(modifier = Modifier.padding(contentPadding))

    }
}

@Composable
@Preview(showBackground = true)
private fun EventListScreenPreview() {
    AppTheme {
        EventListScreen()
    }
}