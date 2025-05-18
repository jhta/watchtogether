package com.watchtogether.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import com.watchtogether.components.AppScaffold
import com.watchtogether.ui.viewmodels.CreateGroupViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateGroupScreen(
    onBackClick: () -> Unit = {},
    onCreateSuccess: (Int) -> Unit = {},
    viewModel: CreateGroupViewModel = koinViewModel()
) {
    // Get UI state from ViewModel
    val uiState by viewModel.uiState.collectAsState()
    
    // Local state for the group name input
    var groupName by remember { mutableStateOf("") }
    
    // Dialog control state
    var showInvitationDialog by remember { mutableStateOf(false) }
    
    // Success effect to navigate after group creation
    LaunchedEffect(uiState.success) {
        if (uiState.success && uiState.createdGroup != null) {
            showInvitationDialog = true
        }
    }
    
    val context = LocalContext.current
    
    AppScaffold(
        title = "Create Group",
        showBackButton = true,
        onBackClick = onBackClick
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Title
                Text(
                    text = "Create a New Group",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Group name input field
                OutlinedTextField(
                    value = groupName,
                    onValueChange = { groupName = it },
                    label = { Text("Group Name") },
                    placeholder = { Text("Enter group name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !uiState.isLoading
                )
                
                // Error message
                if (uiState.error != null) {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Create button
                Button(
                    onClick = { viewModel.createGroup(groupName) },
                    enabled = groupName.isNotEmpty() && !uiState.isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .height(24.dp)
                                .width(24.dp)
                        )
                    } else {
                        Text("Create Group")
                    }
                }
            }
        }
        
        // Invitation Code Dialog
        if (showInvitationDialog && uiState.createdGroup != null) {
            val createdGroup = uiState.createdGroup!!
            
            AlertDialog(
                onDismissRequest = { /* Don't dismiss on outside click */ },
                title = { Text("Group Created!") },
                text = { 
                    Column {
                        Text(
                            "Your group \"${createdGroup.name}\" has been created. Share this invitation code with others to join:"
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Invitation code card
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = uiState.invitationCode,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Copy button
                                    IconButton(
                                        onClick = { 
                                            copyToClipboard(context, uiState.invitationCode)
                                            Toast.makeText(context, "Code copied to clipboard", Toast.LENGTH_SHORT).show()
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy code"
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.width(8.dp))
                                    
                                    // Share button
                                    IconButton(
                                        onClick = { 
                                            shareInvitationCode(context, createdGroup.name, uiState.invitationCode)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "Share code"
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = { 
                            showInvitationDialog = false
                            viewModel.resetState()
                            onCreateSuccess(createdGroup.id)
                        }
                    ) {
                        Text("Continue")
                    }
                }
            )
        }
    }
}

// Helper function to copy text to clipboard
private fun copyToClipboard(context: Context, text: String) {
    val clipboard = getSystemService(context, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("Invitation Code", text)
    clipboard?.setPrimaryClip(clip)
}

// Helper function to share invitation code
private fun shareInvitationCode(context: Context, groupName: String, code: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Join my WatchTogether group")
        putExtra(Intent.EXTRA_TEXT, "Join my WatchTogether group \"$groupName\" with invitation code: $code")
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share invitation code"))
} 