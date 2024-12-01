package edu.kwjw.you.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import edu.kwjw.you.presentation.ui.screen.EventListScreen

@Composable
internal fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = EventList
    ) {
        composable<EventList> { EventListScreen() }
    }
}