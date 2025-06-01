package com.watchtogether.screens.creategroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.watchtogether.components.AppScaffold
import com.watchtogether.screens.creategroup.components.GroupNameInput
import com.watchtogether.screens.creategroup.components.InvitationCodeDialog
import com.watchtogether.screens.creategroup.modifiers.CreateGroupModifiers
import com.watchtogether.ui.modifiers.CommonModifiers
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
    
    // Dialog control state
    var showInvitationDialog by remember { mutableStateOf(false) }
    
    // Success effect to show dialog after group creation
    LaunchedEffect(uiState.success) {
        if (uiState.success && uiState.createdGroup != null) {
            showInvitationDialog = true
        }
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
                groupName = uiState.name,
                onGroupNameChange = { viewModel.updateName(it) },
                enabled = !uiState.isLoading,
                errorMessage = uiState.error
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Group description input (optional)
            OutlinedTextField(
                value = uiState.description,
                onValueChange = { viewModel.updateDescription(it) },
                label = { Text("Description (Optional)") },
                enabled = !uiState.isLoading,
                modifier = CommonModifiers.inputField(),
                singleLine = false,
                maxLines = 3
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Create button
            Button(
                onClick = { viewModel.createGroup() },
                enabled = uiState.name.isNotEmpty() && !uiState.isLoading,
                modifier = CreateGroupModifiers.createButton()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Create Group")
                }
            }
        }
        
        // Invitation Code Dialog
        if (showInvitationDialog && uiState.createdGroup != null) {
            val createdGroup = uiState.createdGroup!!
            
            InvitationCodeDialog(
                showDialog = true,
                groupName = createdGroup.name,
                invitationCode = uiState.invitationCode,
                onDismiss = {
                    showInvitationDialog = false
                    viewModel.resetState()
                },
                onConfirm = {
                    showInvitationDialog = false
                    viewModel.resetState()
                    onCreateSuccess(createdGroup.id)
                }
            )
        }
    }
} 