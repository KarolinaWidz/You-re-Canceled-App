package edu.kwjw.you.presentation.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.theme.AppTheme
import edu.kwjw.you.presentation.ui.theme.PaddingMedium

//todo: add any design here
//todo: extract shared text fields
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
            .padding(PaddingMedium)
    ) {
        LoginTextField(login = login, onLoginChanged = onLoginChanged)
        PasswordTextField(password = password, onPasswordChanged = onPasswordChanged)
        SignInButton(onSignInClicked = onSignOnClicked)
    }
}

@Composable
internal fun LoginTextField(
    modifier: Modifier = Modifier,
    login: String,
    onLoginChanged: (String) -> Unit,
    isError: Boolean = false
) {
    OutlinedTextField(
        modifier = modifier,
        singleLine = true,
        value = login,
        onValueChange = onLoginChanged,
        isError = isError
    )
}

@Composable
internal fun PasswordTextField(
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChanged: (String) -> Unit,
    isError: Boolean = false
) {
    OutlinedTextField(
        modifier = modifier,
        singleLine = true,
        value = password,
        onValueChange = onPasswordChanged,
        isError = isError,
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
private fun SignInButton(
    modifier: Modifier = Modifier,
    onSignInClicked: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onSignInClicked
    ) {
        Text(text = stringResource(R.string.sign_in))
    }
}

@Composable
@PreviewLightDark
private fun SignInPreview() {
    AppTheme {
        SignIn()
    }
}