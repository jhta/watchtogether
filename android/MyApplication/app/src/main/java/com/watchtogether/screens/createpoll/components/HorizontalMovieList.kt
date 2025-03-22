package com.watchtogether.screens.createpoll.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Movie
import com.watchtogether.screens.createpoll.modifiers.CreatePollModifiers

@Composable
fun HorizontalMovieList(
    title: String,
    movies: List<Movie>,
    selectedMovieIds: Set<String>,
    onMovieToggle: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        if (movies.isEmpty()) {
            Text(
                text = "No movies available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(movies) { movie ->
                    HorizontalMovieItem(
                        movie = movie,
                        isSelected = movie.id in selectedMovieIds,
                        onToggleSelection = { onMovieToggle(movie) },
                        modifier = Modifier.width(150.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
} 