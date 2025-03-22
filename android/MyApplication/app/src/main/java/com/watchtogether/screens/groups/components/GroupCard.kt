package com.watchtogether.screens.groups.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.models.Group
import com.watchtogether.screens.groups.modifiers.GroupsModifiers

@Composable
fun GroupCard(
    group: Group,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = GroupsModifiers.groupCard
            .then(modifier)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = GroupsModifiers.groupCardContent,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Group icon
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = GroupsModifiers.groupIcon,
                tint = MaterialTheme.colorScheme.primary
            )
            
            // Group info
            Column(
                modifier = Modifier.weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = group.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${group.participantCount} participants",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
} 