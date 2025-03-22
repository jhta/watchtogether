package com.watchtogether.screens.createpoll.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.screens.createpoll.modifiers.CreatePollModifiers

@Composable
fun PollTitleStep(
    pollTitle: String,
    onPollTitleChange: (String) -> Unit,
    onNextStep: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Name Your Poll",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            Text(
                text = "Give your poll a descriptive title that helps group members understand what they're voting on.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            OutlinedTextField(
                value = pollTitle,
                onValueChange = onPollTitleChange,
                label = { Text("Poll Title") },
                placeholder = { Text("e.g., Weekend Movie Night") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { 
                        if (pollTitle.isNotEmpty()) {
                            onNextStep()
                        }
                    }
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Press Enter when done",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
} 