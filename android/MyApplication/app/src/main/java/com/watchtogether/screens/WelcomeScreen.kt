package com.watchtogether.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.R
import com.watchtogether.components.AppScaffold
import com.watchtogether.ui.theme.WatchTogetherTheme
import com.watchtogether.ui.viewmodels.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(
    onSignInSuccess: () -> Unit = {},
    viewModel: AuthViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Effect to handle navigation when logged in
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn && uiState.user != null) {
            Log.d("WelcomeScreen", "User is logged in, navigating to Home")
            onSignInSuccess()
        }
    }
    
    // If already logged in, don't render the rest of the screen
    if (uiState.isLoggedIn && uiState.user != null) {
        return
    }
    
    AppScaffold(
        title = "",  // Empty title for welcome screen
        showBackButton = false  // No back button on welcome screen
    ) { innerPadding ->
        // This Box needs to use the innerPadding from the Scaffold
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),  // Apply the padding from Scaffold
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Welcome text in the middle
                Text(
                    text = "Welcome to WatchTogether",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Watch movies and decide what to watch together with your friends",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Error message if any
                if (uiState.error != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Push button to the bottom with a spacer
                Spacer(modifier = Modifier.weight(1f))
                
                // Google sign in button
                OutlinedButton(
                    onClick = { viewModel.signInWithGoogle() },
                    enabled = !uiState.isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_google),
                                contentDescription = "Google",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sign in with Google")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WatchTogetherTheme {
        WelcomeScreen()
    }
} 