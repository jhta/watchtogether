package com.watchtogether.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.models.Movie

@Composable
fun GridMovieSection(
    title: String,
    movies: List<Movie>,
    selectedMovies: Set<Movie>,
    onToggleSelection: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    if (movies.isNotEmpty()) {
        Column(modifier = modifier) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            // Create a grid of 2 columns
            val columns = 2
            val rows = (movies.size + columns - 1) / columns
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                for (row in 0 until rows) {
                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                    ) {
                        for (col in 0 until columns) {
                            val index = row * columns + col
                            if (index < movies.size) {
                                androidx.compose.foundation.layout.Box(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    MovieCard(
                                        movie = movies[index],
                                        isSelected = selectedMovies.contains(movies[index]),
                                        onToggleSelection = onToggleSelection
                                    )
                                }
                            }
                        }
                    }
                    if (row < rows - 1) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
} 