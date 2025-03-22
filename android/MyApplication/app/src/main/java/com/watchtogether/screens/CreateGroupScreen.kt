package com.watchtogether.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import com.watchtogether.components.AppScaffold
import java.util.UUID

@Composable
fun CreateGroupScreen(
    onBackClick: () -> Unit = {},
    onCreateGroup: (String) -> Unit = {}
) {
    // State for the group name input
    val groupName = remember { mutableStateOf("") }
    
    // State for showing the invitation code dialog
    val showInvitationDialog = remember { mutableStateOf(false) }
    
    // Generate a unique invitation code (in a real app, this would be more sophisticated)
    val invitationCode = remember { 
        UUID.randomUUID().toString().substring(0, 8).uppercase() 
    }
    
    val context = LocalContext.current
    
    AppScaffold(
        title = "Create Group",
        showBackButton = true,
        onBackClick = onBackClick
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
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
                value = groupName.value,
                onValueChange = { groupName.value = it },
                label = { Text("Group Name") },
                placeholder = { Text("Enter group name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Create button
            Button(
                onClick = { 
                    if (groupName.value.isNotEmpty()) {
                        // Show invitation dialog before completing group creation
                        showInvitationDialog.value = true
                    }
                },
                enabled = groupName.value.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Group")
            }
        }
        
        // Invitation Code Dialog
        if (showInvitationDialog.value) {
            AlertDialog(
                onDismissRequest = { /* Don't dismiss on outside click */ },
                title = { Text("Group Created!") },
                text = { 
                    Column {
                        Text(
                            "Your group \"${groupName.value}\" has been created. Share this invitation code with others to join:"
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
                                    text = invitationCode,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Copy button with FileCopy icon instead of ContentCopy
                                    IconButton(
                                        onClick = { 
                                            copyToClipboard(context, invitationCode)
                                            Toast.makeText(context, "Code copied to clipboard", Toast.LENGTH_SHORT).show()
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,  // Using FileCopy icon
                                            contentDescription = "Copy code"
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.width(8.dp))
                                    
                                    // Share button
                                    IconButton(
                                        onClick = { 
                                            shareInvitationCode(context, groupName.value, invitationCode)
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
                            showInvitationDialog.value = false
                            onCreateGroup(groupName.value)
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