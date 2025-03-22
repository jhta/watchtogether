package com.watchtogether.screens.createpoll.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Movie
import com.watchtogether.screens.createpoll.modifiers.CreatePollModifiers

@Composable
fun SelectedMoviesList(
    selectedMovies: List<Movie>,
    onRemoveMovie: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    if (selectedMovies.isNotEmpty()) {
        Column(
            modifier = modifier.then(CreatePollModifiers.selectedMoviesSection())
        ) {
            Text(
                text = "Selected Movies (${selectedMovies.size})",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Divider()
            
            Spacer(modifier = Modifier.height(8.dp))
            
            selectedMovies.forEach { movie ->
                MovieItem(
                    movie = movie,
                    isSelected = true,
                    onToggleSelection = { onRemoveMovie(movie) }
                )
            }
        }
    }
} 