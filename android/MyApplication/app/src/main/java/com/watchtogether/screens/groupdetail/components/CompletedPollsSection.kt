package com.watchtogether.screens.groupdetail.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.models.Poll

@Composable
fun CompletedPollsSection(
    completedPolls: List<Poll>,
    onPollClick: (Poll) -> Unit
) {
    Text(
        text = "Completed Polls",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    
    if (completedPolls.isEmpty()) {
        Text(
            text = "No completed polls",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        completedPolls.forEach { poll ->
            PollItem(
                poll = poll,
                onClick = { onPollClick(poll) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    
    // Add space at the bottom for the FAB
    Spacer(modifier = Modifier.height(80.dp))
} 