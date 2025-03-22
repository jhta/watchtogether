package com.watchtogether.screens.createpoll.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun PersistentBottomBar(
    selectedCount: Int,
    onCreatePollClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // This Box will overlay the button at the bottom of the screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(10f) // Ensure it's above other content
    ) {
        // Transparent button with rounded corners
        Button(
            onClick = onCreatePollClick,
            enabled = selectedCount > 0,
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )
        ) {
            Text(
                text = "Create Poll (${selectedCount})",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
} 