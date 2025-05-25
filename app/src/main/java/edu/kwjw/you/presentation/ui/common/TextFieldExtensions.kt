package edu.kwjw.you.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import edu.kwjw.you.presentation.ui.theme.PaddingSmall

@Composable
internal fun TextFieldError(stringId: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier.padding(end = PaddingSmall),
            imageVector = Icons.Filled.Info,
            tint = MaterialTheme.colorScheme.error,
            contentDescription = null
        )
        Text(text = stringResource(stringId), color = MaterialTheme.colorScheme.error)
    }
}

@Composable
internal fun ClickableTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: () -> Unit,
    isError: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isClicked: Boolean = false,
    onIsClickedChanged: (Boolean) -> Unit,
    onClicked: @Composable (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        modifier = modifier.clickable(
            onClick = { onIsClickedChanged(true) },
            interactionSource = interactionSource,
            indication = null
        ),
        value = value,
        onValueChange = { onValueChange },
        label = label,
        isError = isError,
        supportingText = supportingText,
        enabled = false,
        leadingIcon = leadingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            disabledLabelColor = colorResolver(
                isError,
                OutlinedTextFieldDefaults.colors().unfocusedLabelColor,
                OutlinedTextFieldDefaults.colors().errorLabelColor
            ),
            disabledTextColor = colorResolver(
                isError,
                OutlinedTextFieldDefaults.colors().unfocusedTextColor,
                OutlinedTextFieldDefaults.colors().errorTextColor
            ),
            disabledBorderColor = colorResolver(
                isError,
                OutlinedTextFieldDefaults.colors().unfocusedIndicatorColor,
                OutlinedTextFieldDefaults.colors().errorIndicatorColor
            ),
            disabledLeadingIconColor = colorResolver(
                isError,
                OutlinedTextFieldDefaults.colors().unfocusedLeadingIconColor,
                OutlinedTextFieldDefaults.colors().errorLeadingIconColor
            )
        )
    )

    if (isClicked) {
        onClicked?.invoke()
    }
}

private fun colorResolver(isError: Boolean, defaultColor: Color, errorColor: Color) =
    if (isError) errorColor else defaultColor