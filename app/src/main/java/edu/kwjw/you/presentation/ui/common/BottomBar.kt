package edu.kwjw.you.presentation.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import edu.kwjw.you.R
import edu.kwjw.you.presentation.ui.theme.AppTheme


@Composable
internal fun BottomBar(
    modifier: Modifier = Modifier,
    onAddNewItemClicked: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {},
    onFriendsClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {}
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.inversePrimary,
        floatingActionButton = { AddItemFab(onClick = onAddNewItemClicked) },
        actions = {
            MenuActions(
                onHomeClicked = onHomeClicked,
                onProfileClicked = onProfileClicked,
                onFriendsClicked = onFriendsClicked,
                onSettingsClicked = onSettingsClicked
            )
        }
    )

}

@Composable
private fun AddItemFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_new_event)
        )
    }
}

@Composable
private fun MenuActions(
    modifier: Modifier = Modifier,
    onHomeClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {},
    onFriendsClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {}
) {
    Row {
        IconButton(modifier = modifier, onClick = onHomeClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_home), contentDescription =
                stringResource(R.string.home_screen),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(modifier = modifier, onClick = onProfileClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_user),
                contentDescription = stringResource(R.string.profile_screen),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(modifier = modifier, onClick = onFriendsClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_friends),
                contentDescription = stringResource(R.string.friends_screen),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(modifier = modifier, onClick = onSettingsClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = "Settings screen",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun BottomBarPreview() {
    AppTheme {
        BottomBar()
    }
}