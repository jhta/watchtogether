package com.watchtogether.screens.groupdetail.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.models.Poll

@Composable
fun ActivePollsSection(
    activePolls: List<Poll>,
    onPollClick: (Poll) -> Unit
) {
    Text(
        text = "Active Polls",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    
    if (activePolls.isEmpty()) {
        Text(
            text = "No active polls",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        activePolls.forEach { poll ->
            PollItem(
                poll = poll,
                onClick = { onPollClick(poll) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    
    Divider(modifier = Modifier.padding(vertical = 8.dp))
} 