package com.watchtogether.screens

import com.watchtogether.screens.groups.GroupsScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.watchtogether.components.AppScaffold
import com.watchtogether.ui.theme.WatchTogetherTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.watchtogether.components.AppBottomNavigation
import com.watchtogether.components.AppTopBar
import com.watchtogether.components.BottomNavItem

@Composable
fun HomeScreen(
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onCreateGroupClick: () -> Unit = {},
    onGroupClick: (String) -> Unit = {}
) {
    // Create a nested NavController for the bottom navigation
    val navController = rememberNavController()
    
    Scaffold(
        topBar = {
            AppTopBar(
                title = "WatchTogether",
                showBackButton = showBackButton,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        // Nested NavHost for the bottom navigation
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Groups.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Groups.route) {
                GroupsScreen(
                    onCreateGroupClick = onCreateGroupClick,
                    onGroupClick = onGroupClick
                )
            }
            composable(BottomNavItem.WatchList.route) {
                WatchListScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WatchTogetherTheme {
        HomeScreen()
    }
} 