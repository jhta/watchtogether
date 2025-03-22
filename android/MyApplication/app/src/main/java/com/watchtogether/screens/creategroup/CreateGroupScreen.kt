package com.watchtogether.screens.creategroup

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.watchtogether.components.AppScaffold
import com.watchtogether.screens.creategroup.components.GroupNameInput
import com.watchtogether.screens.creategroup.components.InvitationCodeDialog
import com.watchtogether.screens.creategroup.modifiers.CreateGroupModifiers
import com.watchtogether.ui.modifiers.CommonModifiers
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
    
    // Generate a unique invitation code
    val invitationCode = remember { 
        UUID.randomUUID().toString().substring(0, 8).uppercase() 
    }
    
    AppScaffold(
        title = "Create Group",
        showBackButton = true,
        onBackClick = onBackClick
    ) { innerPadding ->
        Column(
            modifier = CommonModifiers.screenContainer(innerPadding)
        ) {
            // Group name input
            GroupNameInput(
                groupName = groupName.value,
                onGroupNameChange = { groupName.value = it }
            )
            
            // Create button
            Button(
                onClick = { 
                    if (groupName.value.isNotEmpty()) {
                        showInvitationDialog.value = true
                    }
                },
                enabled = groupName.value.isNotEmpty(),
                modifier = CreateGroupModifiers.createButton()
            ) {
                Text("Create Group")
            }
        }
        
        // Invitation Code Dialog
        InvitationCodeDialog(
            showDialog = showInvitationDialog.value,
            groupName = groupName.value,
            invitationCode = invitationCode,
            onDismiss = { showInvitationDialog.value = false },
            onConfirm = {
                showInvitationDialog.value = false
                onCreateGroup(groupName.value)
            }
        )
    }
} 