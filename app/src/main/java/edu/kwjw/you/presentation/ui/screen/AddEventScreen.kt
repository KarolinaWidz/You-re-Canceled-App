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
import androidx.hilt.navigation.compose.hiltViewModel
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.addnewevent.AddEvent
import edu.kwjw.you.presentation.ui.common.TopTitleBarWithBackButton
import edu.kwjw.you.presentation.ui.theme.AppTheme
import edu.kwjw.you.presentation.uiState.AddEventIntent
import edu.kwjw.you.presentation.uiState.UiState
import edu.kwjw.you.presentation.viewModel.AddEventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddEventScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit = {},
    viewModel: AddEventViewModel = hiltViewModel()
) {

    val title = stringResource(R.string.add_new_event)
    val snackbarHost = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopTitleBarWithBackButton(
                title = title,
                onBackButtonClicked = goBack
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        snackbarHost = { SnackbarHost(hostState = snackbarHost) }
    ) { contentPadding ->
        AddEvent(
            modifier = Modifier.padding(contentPadding),
            eventName = state.name,
            onEventNameChanged = { name -> viewModel.processIntent(AddEventIntent.UpdateName(name = name)) },
            isEventNameError = state.isNameError,
            eventDate = state.rawDate,
            onEventDateChanged = { date ->
                viewModel.processIntent(
                    AddEventIntent.UpdateDate(
                        timestamp = date
                    )
                )
            },
            isEventDateError = state.isDateError,
            eventTime = state.rawTime,
            onEventTimeChanged = { time ->
                viewModel.processIntent(
                    AddEventIntent.UpdateTime(time = time)
                )
            },
            isEventTimeError = state.isTimeError,
            onAddItemClicked = { viewModel.processIntent(AddEventIntent.SaveEvent) }
        )

        LaunchedEffect(state.uiState) {
            when (state.uiState) {
                UiState.Error -> {
                    //todo show snackbar
                }
                UiState.Success -> goBack()
                UiState.Idle -> {
                    /* intentionally empty */
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@PreviewLightDark
private fun EventEventScreenPreview() {
    AppTheme {
        AddEventScreen()
    }
}