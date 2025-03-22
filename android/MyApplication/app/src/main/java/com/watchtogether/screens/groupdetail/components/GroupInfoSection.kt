package com.watchtogether.screens.groupdetail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.models.Group

@Composable
fun GroupInfoSection(group: Group) {
    Text(
        text = "Group Details",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    
    Text(
        text = "${group.participantCount} members",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    
    Divider(modifier = Modifier.padding(vertical = 8.dp))
} 