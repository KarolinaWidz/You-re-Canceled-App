package edu.kwjw.you.presentation.ui.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.theme.PaddingExtraExtraLarge
import edu.kwjw.you.presentation.ui.theme.PaddingSmall

@Composable
internal fun LoginTextField(
    login: String,
    onLoginChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = PaddingSmall),
        singleLine = true,
        value = login,
        onValueChange = onLoginChanged,
        isError = isError,
        label = { Text(stringResource(R.string.type_your_email)) },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_email),
                contentDescription = null
            )
        },
    )
}

@Composable
internal fun PasswordTextField(
    password: String,
    onPasswordChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = PaddingSmall),
        singleLine = true,
        value = password,
        onValueChange = onPasswordChanged,
        isError = isError,
        label = { Text(stringResource(R.string.type_your_password)) },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_password_lock),
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                PasswordVisibilityIcon(passwordVisible)
            }
        },
        visualTransformation = passwordVisualTransformation(passwordVisible)
    )
}

@Composable
internal fun SignInButton(
    text: String,
    modifier: Modifier = Modifier,
    onSignInClicked: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = PaddingExtraExtraLarge),
        onClick = onSignInClicked
    ) {
        Text(text = text)
    }
}

@Composable
private fun PasswordVisibilityIcon(
    passwordVisible: Boolean, modifier: Modifier = Modifier
) {
    val (painterResource, contentDescription) = if (passwordVisible) {
        R.drawable.ic_visibility_off to R.string.hide_password
    } else {
        R.drawable.ic_visibility to R.string.show_password
    }
    Icon(
        modifier = modifier,
        painter = painterResource(painterResource),
        contentDescription = stringResource(contentDescription)
    )
}

private fun passwordVisualTransformation(passwordVisible: Boolean) =
    if (passwordVisible) VisualTransformation.None
    else PasswordVisualTransformation()
