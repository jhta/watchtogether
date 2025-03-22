package com.watchtogether.screens.polldetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Movie

@Composable
fun MovieCard(
    movie: Movie,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(300.dp)  // Fixed width for the card
            .height(500.dp)
            .clickable(onClick = onSelect),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Movie poster
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isSelected) 
                            MaterialTheme.colorScheme.primaryContainer 
                        else 
                            MaterialTheme.colorScheme.secondaryContainer
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Movie,
                    contentDescription = null,
                    tint = if (isSelected) 
                        MaterialTheme.colorScheme.onPrimaryContainer 
                    else 
                        MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
            
            // Movie info overlay
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(16.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text(
                    text = movie.year,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (movie.description.isNotEmpty()) {
                    Text(
                        text = movie.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            // Selection indicator
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(50)
                        )
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
} 