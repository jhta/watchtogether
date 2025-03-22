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
    fun groupDetailRoute(groupId: String): String = "group_detail/$groupId"
    
    // Helper function to create poll creation route with groupId
    fun createPollRoute(groupId: String): String = "create_poll/$groupId"
    
    // Helper function to create poll detail route with pollId
    fun pollDetailRoute(pollId: String): String = "poll/$pollId"
    
    // Helper function to create vote success route with pollId
    fun voteSuccessRoute(pollId: String): String = "vote_success/$pollId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = AppDestinations.WELCOME_ROUTE
    ) {
        composable(AppDestinations.WELCOME_ROUTE) {
            WelcomeScreen(
                onSignUpClicked = { 
                    navController.navigate(AppDestinations.HOME_ROUTE)
                },
                onLoginClicked = { 
                    navController.navigate(AppDestinations.HOME_ROUTE)
                }
            )
        }
        
        composable(AppDestinations.HOME_ROUTE) {
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
                }
            )
        }
        
        composable(AppDestinations.CREATE_GROUP_ROUTE) {
            CreateGroupScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onCreateGroup = { groupName ->
                    // Navigate back to home after creating the group
                    navController.navigateUp()
                    
                    // In a real app, you would save the group to a database here
                    // and then navigate to the group detail screen
                    // navController.navigate(AppDestinations.groupDetailRoute(newGroupId))
                }
            )
        }
        
        composable(
            route = AppDestinations.GROUP_DETAIL_ROUTE,
            arguments = listOf(
                navArgument("groupId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId") ?: ""
            GroupDetailScreen(
                groupId = groupId,
                onBackClick = {
                    navController.navigateUp()
                },
                onCreatePollClick = { groupId ->
                    navController.navigate(AppDestinations.createPollRoute(groupId))
                },
                onPollClick = { pollId ->
                    navController.navigate(AppDestinations.pollDetailRoute(pollId))
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