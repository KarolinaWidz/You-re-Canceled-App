package edu.kwjw.you.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.kwjw.you.presentation.ui.screen.AddEventScreen
import edu.kwjw.you.presentation.ui.screen.EventListScreen
import edu.kwjw.you.presentation.ui.screen.SignInScreen

@Composable
internal fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SignIn
    ) {
        composable<SignIn> { SignInScreen() }

        composable<EventList> {
            EventListScreen(
                onAddNewItemClicked = { navController.navigate(AddNewEvent) }
            )
        }
        composable<AddNewEvent> { AddEventScreen(goBack = { navController.navigateUp() }) }
    }
}