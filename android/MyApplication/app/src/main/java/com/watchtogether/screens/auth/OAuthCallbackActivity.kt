package com.watchtogether.screens.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import com.watchtogether.MainActivity
import com.watchtogether.navigation.AppDestinations
import com.watchtogether.ui.theme.WatchTogetherTheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.handleDeeplinks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

/**
 * Activity that handles OAuth redirects from external providers
 */
class OAuthCallbackActivity : ComponentActivity() {
    
    private val supabase: SupabaseClient by inject()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Log the intent data for debugging
        intent?.data?.let {
            Log.d("OAuthCallback", "Received intent with data: ${it.toString()}")
            Log.d("OAuthCallback", "Scheme: ${it.scheme}, Host: ${it.host}, Path: ${it.path}")
            Log.d("OAuthCallback", "Query parameters: ${it.query}")
            Log.d("OAuthCallback", "Fragment: ${it.fragment}")
        } ?: Log.d("OAuthCallback", "Intent data is null")
        
        setContent {
            WatchTogetherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isLoading by remember { mutableStateOf(true) }
                    var errorMessage by remember { mutableStateOf<String?>(null) }
                    
                    // Handle the OAuth callback
                    LaunchedEffect(Unit) {
                        try {
                            withContext(Dispatchers.IO) {
                                val sessionBefore = supabase.auth.currentSessionOrNull()
                                Log.d("OAuthCallback", "Session before handling deep link: ${sessionBefore != null}")
                                
                                // Process the deep link
                                Log.d("OAuthCallback", "Attempting to handle deep link")
                                val success = supabase.handleDeeplinks(intent)
                                Log.d("OAuthCallback", "Deep link handled: $success")
                                
                                // Give some time for session processing
                                delay(500)
                                
                                // Modern Supabase-kt automatically saves sessions to storage
                                // No need to manually call saveToStorage()
                                
                                // Check session after handling deep link
                                val sessionAfter = supabase.auth.currentSessionOrNull()
                                Log.d("OAuthCallback", "Session after handling deep link: ${sessionAfter != null}")
                                
                                if (sessionAfter != null) {
                                    Log.d("OAuthCallback", "Authentication successful, user: ${sessionAfter.user?.id}")
                                } else {
                                    Log.w("OAuthCallback", "No session found after deep link handling")
                                }
                            }
                            
                            // Navigate to home screen on success
                            goToHomeScreen()
                        } catch (e: RestException) {
                            Log.e("OAuthCallback", "Authentication error", e)
                            errorMessage = e.message ?: "Authentication error"
                            isLoading = false
                        } catch (e: Exception) {
                            Log.e("OAuthCallback", "Unknown error", e)
                            errorMessage = e.message ?: "Unknown error"
                            isLoading = false
                        }
                    }
                    
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isLoading) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Finalizing authentication...")
                            }
                        } else if (errorMessage != null) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    "Authentication Error",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    errorMessage!!,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = { finish() }) {
                                    Text("Back")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private fun goToHomeScreen() {
        Log.d("OAuthCallback", "Navigating to Home Screen")
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            // Add an extra to indicate we want to navigate to the home screen
            putExtra("DESTINATION", AppDestinations.HOME_ROUTE)
        }
        startActivity(intent)
        finish()
    }
} 