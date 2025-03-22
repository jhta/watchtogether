package com.watchtogether.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Utility class to handle navigation-related functionality
 */
class AppNavigationActions(private val navController: NavHostController) {
    fun navigateUp() {
        navController.navigateUp()
    }
    
    fun navigateToHome() {
        navController.navigate(AppDestinations.HOME_ROUTE) {
            popUpTo(AppDestinations.WELCOME_ROUTE) {
                inclusive = false  // Keep welcome in backstack
            }
        }
    }
    
    fun navigateToWelcome() {
        navController.navigate(AppDestinations.WELCOME_ROUTE) {
            popUpTo(0) { inclusive = true }  // Clear backstack
        }
    }
}
