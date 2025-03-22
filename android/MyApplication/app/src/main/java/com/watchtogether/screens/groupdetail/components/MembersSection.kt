package com.watchtogether.screens.groupdetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.models.Member

@Composable
fun MembersSection(members: List<Member>) {
    Text(
        text = "Members",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        items(members) { member ->
            MemberItem(member = member)
        }
    }
    
    Divider(modifier = Modifier.padding(vertical = 8.dp))
} 