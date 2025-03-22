package com.watchtogether.screens.creategroup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InvitationCodeDialog(
    showDialog: Boolean,
    groupName: String,
    invitationCode: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { /* Don't dismiss on outside click */ },
            title = { Text("Group Created!") },
            text = { 
                Column {
                    Text(
                        "Your group \"$groupName\" has been created. Share this invitation code with others to join:"
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Invitation code card
                    InvitationCodeCard(
                        invitationCode = invitationCode,
                        groupName = groupName,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("Continue")
                }
            }
        )
    }
} 