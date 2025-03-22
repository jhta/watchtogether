package com.watchtogether.screens.createpoll.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Movie
import com.watchtogether.screens.createpoll.modifiers.CreatePollModifiers

private const val TAG = "MovieGrid"

@Composable
fun MovieGrid(
    title: String,
    movies: List<Movie>,
    selectedMovieIds: Set<String>,
    onMovieToggle: (Movie) -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    // Log when the component is composed
    LaunchedEffect(movies) {
        Log.d(TAG, "MovieGrid composed with title: $title")
        Log.d(TAG, "Number of movies: ${movies.size}")
        if (movies.isNotEmpty()) {
            Log.d(TAG, "First few movies: ${movies.take(3).map { it.title }}")
        } else {
            Log.d(TAG, "No movies available")
        }
    }
    
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        } else if (movies.isEmpty()) {
            Log.d(TAG, "Showing 'No movies found' message")
            Text(
                text = "No movies found",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        } else {
            Log.d(TAG, "Rendering grid with ${movies.size} movies")
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movies) { movie ->
                    HorizontalMovieItem(
                        movie = movie,
                        isSelected = movie.id in selectedMovieIds,
                        onToggleSelection = { onMovieToggle(movie) }
                    )
                }
            }
        }
    }
} 