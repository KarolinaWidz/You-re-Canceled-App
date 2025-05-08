package edu.kwjw.you.presentation.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.auth.SignIn
import edu.kwjw.you.presentation.ui.common.TopTitleBar
import edu.kwjw.you.presentation.ui.theme.AppTheme
import edu.kwjw.you.presentation.uiState.SignInIntent
import edu.kwjw.you.presentation.uiState.SignInUiState
import edu.kwjw.you.presentation.viewModel.SignInViewModel


@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier,
    goToEventList: () -> Unit = {},
    viewModel: SignInViewModel = hiltViewModel()
) {
    val title = stringResource(R.string.app_name)
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = { TopTitleBar(title) },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { contentPadding ->
        SignIn(
            modifier = Modifier.padding(contentPadding),
            login = state.email,
            onLoginChanged = { login -> viewModel.processIntent(SignInIntent.UpdateLogin(login)) },
            password = state.password,
            onPasswordChanged = { password ->
                viewModel.processIntent(
                    SignInIntent.UpdatePassword(
                        password
                    )
                )
            },
            onSignOnClicked = { viewModel.processIntent(SignInIntent.SignIn) }
        )

        LaunchedEffect(state.uiState) {
            when (state.uiState) {
                SignInUiState.Success -> goToEventList()
                SignInUiState.Error -> {
                    //todo add error handling
                }
                SignInUiState.Idle -> {
                    /* Intentionally empty */
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun SignInScreenPreview() {
    AppTheme {
        SignInScreen()
    }
}