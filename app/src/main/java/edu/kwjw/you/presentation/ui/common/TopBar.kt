package edu.kwjw.you.presentation.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.theme.AppTheme
import edu.kwjw.you.presentation.ui.theme.SpacerMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopTitleBarWithBackButton(
    title: String,
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit = {}
) {
    TopTitleBar(
        modifier = modifier,
        navigationIcon = { BackButtonAction(onBackClicked = onBackButtonClicked) },
        title = title,
        actions = { Spacer(modifier = Modifier.size(SpacerMedium)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopTitleBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        navigationIcon = navigationIcon,
        title = { TitleText(title = title) },
        actions = actions,
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.inverseSurface,
            titleContentColor = MaterialTheme.colorScheme.inverseSurface,
            actionIconContentColor = MaterialTheme.colorScheme.inverseSurface,
            subtitleContentColor = MaterialTheme.colorScheme.inverseSurface,
        )
    )
}

@Composable
private fun TitleText(modifier: Modifier = Modifier, title: String) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun BackButtonAction(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
) {
    Row {
        IconButton(modifier = modifier, onClick = onBackClicked) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.go_back),
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun TopTitleBarPreview() {
    AppTheme {
        TopTitleBar("App title")
    }
}

@Composable
@PreviewLightDark
private fun TopTitleBarWithBackButtonPreview() {
    AppTheme {
        TopTitleBarWithBackButton("App title")
    }
}