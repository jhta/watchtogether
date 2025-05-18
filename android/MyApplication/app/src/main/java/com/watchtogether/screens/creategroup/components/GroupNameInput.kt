package com.watchtogether.screens.creategroup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.screens.creategroup.modifiers.CreateGroupModifiers
import com.watchtogether.ui.modifiers.CommonModifiers

@Composable
fun GroupNameInput(
    groupName: String,
    onGroupNameChange: (String) -> Unit,
    enabled: Boolean = true,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Title
        Text(
            text = "Create a New Group",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = CreateGroupModifiers.title()
        )
        
        // Group name input field
        OutlinedTextField(
            value = groupName,
            onValueChange = onGroupNameChange,
            label = { Text("Group Name") },
            placeholder = { Text("Enter group name") },
            modifier = CommonModifiers.inputField(),
            singleLine = true,
            enabled = enabled
        )
        
        // Error message
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
} 