package com.watchtogether.screens.groupdetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.models.Poll
import com.watchtogether.models.PollStatus
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PollItem(
    poll: Poll,
    onClick: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (poll.status == PollStatus.ACTIVE) 
                        Icons.Default.DateRange else Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = if (poll.status == PollStatus.ACTIVE) 
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = poll.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = poll.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = if (poll.status == PollStatus.ACTIVE) 
                    "Ends: ${poll.endDate?.let { dateFormat.format(it) } ?: "N/A"}" 
                else 
                    "Completed: ${poll.endDate?.let { dateFormat.format(it) } ?: "N/A"}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
} 