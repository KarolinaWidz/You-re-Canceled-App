package edu.kwjw.you.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import edu.kwjw.you.presentation.ui.screen.AddEventScreen
import edu.kwjw.you.presentation.ui.screen.EventListScreen
import edu.kwjw.you.presentation.ui.screen.SignInScreen

@Composable
internal fun AppNavigation() {
    val navController = rememberNavController()
    //todo: extract that to viewmodel
    val startDestination = if (Firebase.auth.currentUser != null) EventList else SignIn
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<SignIn> {
            SignInScreen(
                goToEventList = {
                    navController.navigate(EventList) {
                        popUpTo(SignIn) { inclusive = true }
                    }
                }
            )
        }

        composable<EventList> {
            EventListScreen(
                onAddNewItemClicked = { navController.navigate(AddNewEvent) }
            )
        }
        composable<AddNewEvent> { AddEventScreen(goBack = { navController.navigateUp() }) }
    }
}