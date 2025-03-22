package com.watchtogether.screens.polldetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.watchtogether.components.AppScaffold
import com.watchtogether.models.Movie
import com.watchtogether.screens.polldetail.components.MovieCard
import com.watchtogether.screens.polldetail.components.VoteSuccessScreen
import com.watchtogether.screens.polldetail.modifiers.PollDetailModifiers

@Composable
fun PollDetailScreen(
    pollTitle: String,
    onBackClick: () -> Unit = {},
    onVoteComplete: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Mock data for movies
    val movies = remember {
        listOf(
            Movie(
                id = "1",
                title = "Inception",
                year = "2010",
                description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O."
            ),
            Movie(
                id = "2",
                title = "The Dark Knight",
                year = "2008",
                description = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice."
            ),
            Movie(
                id = "3",
                title = "Interstellar",
                year = "2014",
                description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival."
            ),
            Movie(
                id = "4",
                title = "The Matrix",
                year = "1999",
                description = "A computer programmer discovers that reality as he knows it is a simulation created by machines, and joins a rebellion to break free."
            )
        )
    }
    
    // State to track the user's vote
    var votedMovie by remember { mutableStateOf<Movie?>(null) }
    var showSuccessScreen by remember { mutableStateOf(false) }
    
    if (showSuccessScreen) {
        VoteSuccessScreen(
            onComplete = onVoteComplete,
            modifier = modifier
        )
    } else {
        AppScaffold(
            title = "Poll Details",
            showBackButton = true,
            onBackClick = onBackClick
        ) { innerPadding ->
            Column(
                modifier = PollDetailModifiers.rootContainer
                    .then(modifier)
                    .padding(innerPadding)
            ) {
                // Poll title
                Text(
                    text = pollTitle,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = PollDetailModifiers.pollTitle
                )
                
                // Poll status
                Text(
                    text = if (votedMovie != null) 
                        "You voted for: ${votedMovie?.title}"
                    else 
                        "Select a movie to vote",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = PollDetailModifiers.pollStatus
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Movie cards carousel
                LazyRow(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        start = 16.dp,
                        end = 16.dp
                    ),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
                ) {
                    items(movies) { movie ->
                        MovieCard(
                            movie = movie,
                            isSelected = movie == votedMovie,
                            onSelect = { votedMovie = movie }
                        )
                    }
                }
                
                // Confirm button
                Button(
                    onClick = { showSuccessScreen = true },
                    enabled = votedMovie != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Confirm Vote")
                }
            }
        }
    }
} 