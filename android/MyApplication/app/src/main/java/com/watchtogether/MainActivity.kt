package com.watchtogether

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.watchtogether.navigation.AppDestinations
import com.watchtogether.navigation.AppNavigation
import com.watchtogether.ui.theme.WatchTogetherTheme

class MainActivity : ComponentActivity() {
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
                    AppNavigation(initialRoute = initialDestination ?: AppDestinations.WELCOME_ROUTE)
                }
            }
        }
    }
}