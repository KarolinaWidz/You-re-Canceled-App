package edu.kwjw.you.presentation.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.theme.AppTheme
import edu.kwjw.you.presentation.ui.theme.PaddingLarge
import edu.kwjw.you.presentation.ui.theme.PaddingMedium
import edu.kwjw.you.util.validation.InputTextValidator

@Composable
internal fun SignIn(
    modifier: Modifier = Modifier,
    email: String = "",
    onEmailChanged: (String) -> Unit = {},
    isEmailError: Boolean = false,
    emailErrorType: InputTextValidator.Error? = null,
    password: String = "",
    onPasswordChanged: (String) -> Unit = {},
    isPasswordError: Boolean = false,
    passwordErrorType: InputTextValidator.Error? = null,
    isSignInButtonEnabled: Boolean = false,
    onSignOnClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(PaddingLarge)
    ) {
        Subtitle()
        EmailTextField(
            email = email,
            onLoginChanged = onEmailChanged,
            isError = isEmailError,
            errorStringId = resolveEmailErrorString(emailErrorType)
        )
        PasswordTextField(
            password = password, onPasswordChanged = onPasswordChanged,
            isError = isPasswordError,
            errorStringId = resolvePasswordErrorString(passwordErrorType)
        )
        SignInButton(
            isEnabled = isSignInButtonEnabled,
            text = stringResource(R.string.sign_in),
            onSignInClicked = onSignOnClicked
        )
    }
}

@Composable
private fun Subtitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(bottom = PaddingMedium),
        text = stringResource(R.string.give_credential_to_sign_in_your_account),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

private fun resolveEmailErrorString(errorType: InputTextValidator.Error?) = when (errorType) {
    InputTextValidator.Error.Empty -> R.string.email_field_cannot_be_empty
    InputTextValidator.Error.IncorrectEmailFormat -> R.string.the_email_address_format_is_invalid
    InputTextValidator.Error.MaxLengthExceeded -> R.string.email_must_be_100_characters_or_fewer
    else -> null
}

private fun resolvePasswordErrorString(errorType: InputTextValidator.Error?) = when (errorType) {
    InputTextValidator.Error.Empty -> R.string.password_field_cannot_be_empty
    InputTextValidator.Error.MaxLengthExceeded -> R.string.password_exceeds_the_allowed_length
    else -> null
}

@Composable
@PreviewLightDark
private fun SignInPreview() {
    AppTheme {
        SignIn()
    }
}