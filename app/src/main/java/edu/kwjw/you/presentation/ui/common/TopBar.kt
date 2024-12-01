package edu.kwjw.you.presentation.ui.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import edu.kwjw.you.presentation.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopTitleBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = { }
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
@Preview(showBackground = true)
private fun DatePickerDialogPreview() {
    AppTheme {
        TopTitleBar("App title")
    }
}