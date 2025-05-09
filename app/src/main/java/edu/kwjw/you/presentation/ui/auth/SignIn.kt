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

@Composable
internal fun SignIn(
    modifier: Modifier = Modifier,
    login: String = "",
    onLoginChanged: (String) -> Unit = {},
    password: String = "",
    onPasswordChanged: (String) -> Unit = {},
    onSignOnClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(PaddingLarge)
    ) {
        Subtitle()
        LoginTextField(login = login, onLoginChanged = onLoginChanged)
        PasswordTextField(password = password, onPasswordChanged = onPasswordChanged)
        SignInButton(text = stringResource(R.string.sign_in), onSignInClicked = onSignOnClicked)
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

@Composable
@PreviewLightDark
private fun SignInPreview() {
    AppTheme {
        SignIn()
    }
}