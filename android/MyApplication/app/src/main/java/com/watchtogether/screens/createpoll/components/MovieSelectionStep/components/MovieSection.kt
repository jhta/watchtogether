package com.watchtogether.screens.createpoll.components.MovieSelectionStep.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Movie
import com.watchtogether.screens.createpoll.components.HorizontalMovieItem
import com.watchtogether.screens.createpoll.components.MovieSelectionStep.modifiers.MovieSelectionModifiers
import com.watchtogether.screens.createpoll.components.MovieSelectionStep.components.MovieGridItem
import com.watchtogether.screens.createpoll.components.ReviewBottomSheet.ReviewBottomSheet

@Composable
fun HorizontalMovieSection(
    title: String,
    movies: List<Movie>,
    selectedMovies: Set<Movie>,
    onToggleSelection: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    if (movies.isEmpty()) return
    
    Column(modifier = modifier) {
        SectionHeader(
            title = title,
            count = movies.size,
            modifier = MovieSelectionModifiers.sectionHeader
        )
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = MovieSelectionModifiers.sectionContent
        ) {
            items(movies) { movie ->
                HorizontalMovieItem(
                    movie = movie,
                    isSelected = movie in selectedMovies,
                    onToggleSelection = { onToggleSelection(movie) },
                    modifier = Modifier.width(150.dp)
                )
            }
        }
    }
}

@Composable
fun GridMovieSection(
    title: String,
    movies: List<Movie>,
    selectedMovies: Set<Movie>,
    onToggleSelection: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    if (movies.isEmpty()) return
    
    Column(modifier = modifier) {
        SectionHeader(
            title = title,
            count = movies.size,
            modifier = MovieSelectionModifiers.sectionHeader
        )
        
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = MovieSelectionModifiers.sectionContent
                .padding(bottom = 16.dp)
                .height((movies.size / 2 * 250).coerceAtMost(1000).dp)
        ) {
            items(movies) { movie ->
                MovieGridItem(
                    movie = movie,
                    isSelected = movie in selectedMovies,
                    onToggleSelection = { onToggleSelection(movie) }
                )
            }
        }
    }
} 