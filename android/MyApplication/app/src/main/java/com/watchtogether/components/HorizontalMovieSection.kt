package com.watchtogether.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.watchtogether.models.Movie

@Composable
fun HorizontalMovieSection(
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
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
            ) {
                movies.forEach { movie ->
                    MovieCard(
                        movie = movie,
                        isSelected = selectedMovies.contains(movie),
                        onToggleSelection = onToggleSelection,
                        modifier = Modifier.width(160.dp)
                    )
                }
            }
        }
    }
} 