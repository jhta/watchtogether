package com.watchtogether.screens.createpoll.components.MovieSelectionStep.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Movie

@Composable
fun MovieGridItem(
    movie: Movie,
    isSelected: Boolean,
    onToggleSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onToggleSelection() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            Box {
                // Movie poster placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f/3f)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    // Movie icon placeholder
                    Icon(
                        imageVector = Icons.Default.Movie,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier
                            .fillMaxSize(0.5f)
                    )
                }
                
                // Selection indicator
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(50)
                            )
                            .border(
                                width = 2.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(50)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
            
            // Movie title
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
} 