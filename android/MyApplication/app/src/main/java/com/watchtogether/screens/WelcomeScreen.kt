package com.watchtogether.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.components.AppScaffold
import com.watchtogether.ui.theme.WatchTogetherTheme

@Composable
fun WelcomeScreen(
    onSignUpClicked: () -> Unit = {},
    onLoginClicked: () -> Unit = {}
) {
    // State to control the visibility of the dialogs
    val showSignUpDialog = remember { mutableStateOf(false) }
    val showLoginDialog = remember { mutableStateOf(false) }
    
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
                
                // Push buttons to the bottom with a spacer
                Spacer(modifier = Modifier.weight(1f))
                
                // Sign Up button (primary)
                Button(
                    onClick = { onSignUpClicked() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Sign Up", modifier = Modifier.padding(vertical = 8.dp))
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Login button (secondary)
                OutlinedButton(
                    onClick = { onLoginClicked() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Login", modifier = Modifier.padding(vertical = 8.dp))
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