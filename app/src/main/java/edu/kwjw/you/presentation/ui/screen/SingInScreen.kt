package edu.kwjw.you.presentation.ui.screen

import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.auth.SignIn
import edu.kwjw.you.presentation.ui.common.TopTitleBar
import edu.kwjw.you.presentation.ui.theme.AppTheme
import edu.kwjw.you.presentation.ui.util.ObserveAsEvents
import edu.kwjw.you.presentation.uiState.SideEffect
import edu.kwjw.you.presentation.uiState.SideEffect.ShowErrorSnackbar.ErrorType
import edu.kwjw.you.presentation.uiState.SignInIntent
import edu.kwjw.you.presentation.uiState.SignInUiState
import edu.kwjw.you.presentation.viewModel.SignInViewModel


@Composable
internal fun SignInScreen(
    modifier: Modifier = Modifier,
    goToEventList: () -> Unit = {},
    viewModel: SignInViewModel = hiltViewModel()
) {
    val title = stringResource(R.string.sign_in)
    val state by viewModel.state.collectAsState()
    val snackbarHost = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = { TopTitleBar(title) },
        snackbarHost = { SnackbarHost(hostState = snackbarHost) },
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { contentPadding ->
        SignIn(
            modifier = Modifier.padding(contentPadding),
            email = state.email,
            onEmailChanged = { login -> viewModel.processIntent(SignInIntent.UpdateEmail(login)) },
            isEmailError = state.isEmailError,
            emailErrorType = state.emailErrorType,
            password = state.password,
            onPasswordChanged = { password ->
                viewModel.processIntent(
                    SignInIntent.UpdatePassword(
                        password
                    )
                )
            },
            isPasswordError = state.isPasswordError,
            passwordErrorType = state.passwordErrorType,
            onSignOnClicked = { viewModel.processIntent(SignInIntent.SignIn) },
            isSignInButtonEnabled = !state.isEmailError && !state.isPasswordError
        )

        ObserveAsEvents(viewModel.sideEffectChannel) { effect ->
            when (effect) {
                is SideEffect.ShowErrorSnackbar -> {
                    snackbarHost.showSnackbar(
                        message = context.getString(
                            resolveSnackbarMessage(
                                errorType = effect.errorType
                            )
                        )
                    )
                }
            }
        }

        LaunchedEffect(state.uiState) {
            when (state.uiState) {
                SignInUiState.Success -> goToEventList()
                else -> {
                    /* Intentionally empty */
                }
            }
        }
    }
}

private fun resolveSnackbarMessage(errorType: ErrorType) =
    when (errorType) {
        ErrorType.FORM_ERROR -> R.string.email_and_password_cannot_be_empty_please_fill_in_both_fields
        ErrorType.SIGN_IN_ERROR -> R.string.failed_to_sign_in_please_check_your_credentials
    }

@Composable
@PreviewLightDark
private fun SignInScreenPreview() {
    AppTheme {
        SignInScreen()
    }
}