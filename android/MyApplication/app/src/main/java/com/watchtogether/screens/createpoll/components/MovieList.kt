package com.watchtogether.screens.createpoll.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Movie
import com.watchtogether.screens.createpoll.modifiers.CreatePollModifiers

@Composable
fun MovieList(
    movies: List<Movie>,
    selectedMovies: Set<String>,
    onMovieToggle: (Movie) -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.then(CreatePollModifiers.movieList())
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (movies.isEmpty()) {
            Text(
                text = "No movies found",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(movies) { movie ->
                    MovieItem(
                        movie = movie,
                        isSelected = selectedMovies.contains(movie.id),
                        onToggleSelection = { onMovieToggle(movie) }
                    )
                }
            }
        }
    }
} 