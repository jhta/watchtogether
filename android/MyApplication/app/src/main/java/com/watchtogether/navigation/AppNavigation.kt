package com.watchtogether.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.watchtogether.screens.creategroup.CreateGroupScreen
import com.watchtogether.screens.groupdetail.GroupDetailScreen
import com.watchtogether.screens.HomeScreen
import com.watchtogether.screens.WelcomeScreen
import com.watchtogether.screens.createpoll.CreatePollScreen
import com.watchtogether.screens.polldetail.PollDetailScreen
import com.watchtogether.screens.polldetail.components.VoteSuccessScreen

// Define navigation routes
object AppDestinations {
    const val WELCOME_ROUTE = "welcome"
    const val HOME_ROUTE = "home"
    const val CREATE_GROUP_ROUTE = "create_group"
    const val GROUP_DETAIL_ROUTE = "group_detail/{groupId}"
    const val CREATE_POLL_ROUTE = "create_poll/{groupId}"
    const val POLL_DETAIL_ROUTE = "poll/{pollId}"
    const val VOTE_SUCCESS_ROUTE = "vote_success/{pollId}"
    
    // Helper function to create group detail route with groupId
    fun groupDetailRoute(groupId: Int): String = "group_detail/$groupId"
    
    // Helper function to create poll creation route with groupId
    fun createPollRoute(groupId: Int): String = "create_poll/$groupId"
    
    // Helper function to create poll detail route with pollId
    fun pollDetailRoute(pollId: String): String = "poll/$pollId"
    
    // Helper function to create vote success route with pollId
    fun voteSuccessRoute(pollId: String): String = "vote_success/$pollId"
}

@Composable
fun AppNavigation(initialRoute: String = AppDestinations.WELCOME_ROUTE) {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = initialRoute
    ) {
        composable(AppDestinations.WELCOME_ROUTE) {
            WelcomeScreen(
                onSignInSuccess = { 
                    navController.navigate(AppDestinations.HOME_ROUTE) {
                        // Clear the back stack so user can't go back to welcome screen after login
                        popUpTo(AppDestinations.WELCOME_ROUTE) { inclusive = true }
                    }
                }
            )
        }
        
        composable(AppDestinations.HOME_ROUTE) {
            val shouldRefresh = navController.currentBackStackEntry
                ?.savedStateHandle
                ?.get<Boolean>("refresh_groups") ?: false
            
            // Clear the refresh flag after reading it
            if (shouldRefresh) {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("refresh_groups", false)
            }
            
            HomeScreen(
                showBackButton = true,
                onBackClick = { 
                    navController.navigateUp() 
                },
                onCreateGroupClick = {
                    navController.navigate(AppDestinations.CREATE_GROUP_ROUTE)
                },
                onGroupClick = { groupId ->
                    navController.navigate(AppDestinations.groupDetailRoute(groupId))
                },
                shouldRefresh = shouldRefresh
            )
        }
        
        composable(AppDestinations.CREATE_GROUP_ROUTE) {
            CreateGroupScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onCreateSuccess = {
                    // Set refresh flag and navigate back to home
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("refresh_groups", true)
                    navController.navigateUp()
                }
            )
        }
        
        composable(
            route = AppDestinations.GROUP_DETAIL_ROUTE,
            arguments = listOf(
                navArgument("groupId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")?.toInt() ?: 0
            GroupDetailScreen(
                groupId = groupId,
                onBackClick = {
                    navController.navigateUp()
                },
                onCreatePollClick = { groupId ->
                    navController.navigate(AppDestinations.createPollRoute(groupId))
                },
                onPollClick = { pollId ->
                    navController.navigate(AppDestinations.pollDetailRoute(pollId.toString()))
                }
            )
        }
        
        composable(
            route = AppDestinations.CREATE_POLL_ROUTE,
            arguments = listOf(
                navArgument("groupId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId") ?: ""
            CreatePollScreen(
                navController = navController,
                modifier = Modifier
            )
        }

        composable(
            route = AppDestinations.POLL_DETAIL_ROUTE,
            arguments = listOf(
                navArgument("pollId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val pollId = backStackEntry.arguments?.getString("pollId") ?: ""
            PollDetailScreen(
                pollTitle = "Movie Poll", // Replace with actual poll title
                onBackClick = {
                    navController.navigateUp()
                },
                onVoteComplete = {
                    navController.navigate(AppDestinations.voteSuccessRoute(pollId))
                }
            )
        }
        
        composable(
            route = AppDestinations.VOTE_SUCCESS_ROUTE,
            arguments = listOf(
                navArgument("pollId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val pollId = backStackEntry.arguments?.getString("pollId") ?: ""
            VoteSuccessScreen(
                onComplete = {
                    // Navigate back to home, clearing the back stack
                    navController.navigate(AppDestinations.HOME_ROUTE) {
                        popUpTo(AppDestinations.HOME_ROUTE) { inclusive = true }
                    }
                }
            )
        }
    }
} 