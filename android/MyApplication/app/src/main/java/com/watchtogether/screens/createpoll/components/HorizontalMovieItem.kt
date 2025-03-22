package com.watchtogether.screens.createpoll.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Movie
import com.watchtogether.screens.createpoll.modifiers.CreatePollModifiers

@Composable
fun HorizontalMovieItem(
    movie: Movie,
    isSelected: Boolean,
    onToggleSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onToggleSelection),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Movie poster
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2/3f)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .then(
                        if (isSelected) 
                            Modifier.border(
                                width = 3.dp, 
                                color = MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.small
                            ) 
                        else 
                            Modifier
                    )
            ) {
                // Movie poster or placeholder
                Icon(
                    imageVector = Icons.Default.Movie,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
                
                // Selection indicator
//                if (isSelected) {
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.TopEnd)
//                            .padding(4.dp)
//                            .background(
//                                color = MaterialTheme.colorScheme.primary,
//                                shape = MaterialTheme.shapes.small
//                            )
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Check,
//                            contentDescription = "Selected",
//                            tint = Color.White,
//                            modifier = Modifier.padding(4.dp)
//                        )
//                    }
//                }
            }
            
            // Movie title
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
            
            // Movie year
            Text(
                text = movie.year,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
    }
} 