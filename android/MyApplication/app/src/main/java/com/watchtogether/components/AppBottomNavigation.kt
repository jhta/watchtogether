package com.watchtogether.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

// Define navigation items
sealed class BottomNavItem(val route: String, val icon: Any, val title: String) {
    object Groups : BottomNavItem("groups", Icons.Default.AccountCircle, "Groups")
    object WatchList : BottomNavItem("watchlist", Icons.Default.List, "Watch List")
}

@Composable
fun AppBottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem.Groups,
        BottomNavItem.WatchList
    )
    
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        
        items.forEach { item ->
            NavigationBarItem(
                icon = { 
                    Icon(
                        imageVector = item.icon as androidx.compose.ui.graphics.vector.ImageVector,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
} 