package com.watchtogether.screens.createpoll.components.ReviewBottomSheet.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Movie
import com.watchtogether.screens.createpoll.components.HorizontalMovieItem

@Composable
fun SelectedMoviesRow(
    selectedMovies: List<Movie>,
    onRemoveMovie: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    if (selectedMovies.isNotEmpty()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            items(selectedMovies) { movie ->
                HorizontalMovieItem(
                    movie = movie,
                    isSelected = true,
                    onToggleSelection = { onRemoveMovie(movie) },
                    modifier = Modifier.width(150.dp)
                )
            }
        }
    } else {
        // Show a message if all movies have been removed
        Text(
            text = "No movies selected",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(vertical = 24.dp)
        )
    }
} 