package com.watchtogether.screens.createpoll.components.MovieSelectionStep.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SectionHeader(
    title: String,
    count: Int? = null,
    modifier: Modifier = Modifier
) {
    val displayText = if (count != null) {
        "$title ($count)"
    } else {
        title
    }
    
    Text(
        text = displayText,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(bottom = 8.dp)
    )
} 