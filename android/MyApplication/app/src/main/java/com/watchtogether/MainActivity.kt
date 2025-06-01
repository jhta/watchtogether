package com.watchtogether

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.watchtogether.navigation.AppDestinations
import com.watchtogether.navigation.AppNavigation
import com.watchtogether.screens.SplashScreen
import com.watchtogether.ui.theme.WatchTogetherTheme
import com.watchtogether.ui.viewmodels.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModel()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the destination from intent extras, if any
        val initialDestination = intent.getStringExtra("DESTINATION")
        if (initialDestination != null) {
            Log.d("MainActivity", "Initial destination: $initialDestination")
        }

        enableEdgeToEdge()
        setContent {
            WatchTogetherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val authState by authViewModel.uiState.collectAsState()
                    
                    // Show splash screen while determining auth state
                    if (!authState.isInitialCheckComplete) {
                        SplashScreen()
                    } else {
                        // Determine initial route based on auth state and intent
                        val startRoute = when {
                            initialDestination != null -> initialDestination
                            authState.isLoggedIn && authState.user != null -> AppDestinations.HOME_ROUTE
                            else -> AppDestinations.WELCOME_ROUTE
                        }
                        
                        Log.d("MainActivity", "Starting with route: $startRoute, User logged in: ${authState.isLoggedIn}")
                        
                        AppNavigation(initialRoute = startRoute)
                    }
                }
            }
        }
    }
}