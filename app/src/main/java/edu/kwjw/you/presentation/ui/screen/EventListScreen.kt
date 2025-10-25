package edu.kwjw.you.presentation.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.common.BottomBar
import edu.kwjw.you.presentation.ui.common.LinearLoadingIndicator
import edu.kwjw.you.presentation.ui.common.TopTitleBar
import edu.kwjw.you.presentation.ui.eventlist.EventList
import edu.kwjw.you.presentation.ui.eventlist.EventListError
import edu.kwjw.you.presentation.ui.theme.AppTheme
import edu.kwjw.you.presentation.ui.theme.ThicknessSmall
import edu.kwjw.you.presentation.uiState.EventListIntent
import edu.kwjw.you.presentation.uiState.EventListUiState
import edu.kwjw.you.presentation.viewModel.EventListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventListScreen(
    modifier: Modifier = Modifier,
    viewModel: EventListViewModel = hiltViewModel(),
    onAddNewItemClicked: () -> Unit = {},
) {
    val title = stringResource(R.string.app_name)
    val snackbarHost = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.processIntent(EventListIntent.GetEvents)
    }
    Scaffold(
        modifier = modifier,
        topBar = { TopTitleBar(title = title) },
        bottomBar = { BottomBar(onAddNewItemClicked = onAddNewItemClicked) },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        snackbarHost = { SnackbarHost(hostState = snackbarHost) }
    ) { contentPadding ->

        when (state.uiState) {
            EventListUiState.Error -> EventListError(onRetry = {
                viewModel.processIntent(
                    EventListIntent.GetEvents
                )
            })

            EventListUiState.Loading -> LinearLoadingIndicator(modifier.padding(contentPadding))

            EventListUiState.Success -> EventList(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(top = ThicknessSmall),
                events = state.events
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun EventListScreenPreview() {
    AppTheme {
        EventListScreen()
    }
}