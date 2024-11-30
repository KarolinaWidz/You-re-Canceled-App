package edu.kwjw.you.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun ClickableTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    onClicked: @Composable (() -> Unit)? = null,
) {
    var isClicked by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        modifier = modifier.clickable(
            onClick = { isClicked = true },
            interactionSource = interactionSource,
            indication = null
        ),
        value = value,
        onValueChange = onValueChange,
        label = label,
        isError = isError,
        supportingText = supportingText,
        enabled = false,
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
        )
    )

    if (isClicked) {
        onClicked?.invoke()
    }
}

private fun colorResolver(isError: Boolean, defaultColor: Color, errorColor: Color) =
    if (isError) errorColor else defaultColor