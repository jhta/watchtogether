package com.watchtogether.screens.createpoll.components.MovieSelectionStep

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Movie
import com.watchtogether.screens.createpoll.components.MovieSelectionStep.components.EmptySearchResults
import com.watchtogether.screens.createpoll.components.MovieSelectionStep.components.GridMovieSection
import com.watchtogether.screens.createpoll.components.MovieSelectionStep.components.HorizontalMovieSection
import com.watchtogether.screens.createpoll.components.MovieSelectionStep.components.SearchBar
import com.watchtogether.screens.createpoll.components.MovieSelectionStep.modifiers.MovieSelectionModifiers
import com.watchtogether.screens.createpoll.components.PersistentBottomBar
import com.watchtogether.screens.createpoll.components.ReviewBottomSheet.ReviewBottomSheet
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

@Composable
fun MovieSelectionStep(
    watchLaterMovies: List<Movie>,
    groupFavoriteMovies: List<Movie>,
    otherMovies: List<Movie>,
    selectedMovies: Set<Movie>,
    onToggleMovieSelection: (Movie) -> Unit,
    onCreatePoll: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showReviewSheet by remember { mutableStateOf(false) }
    
    // Filter movies based on search query
    val filteredWatchLater = remember(watchLaterMovies, searchQuery) {
        if (searchQuery.isEmpty()) watchLaterMovies
        else watchLaterMovies.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }
    
    val filteredGroupFavorites = remember(groupFavoriteMovies, searchQuery) {
        if (searchQuery.isEmpty()) groupFavoriteMovies
        else groupFavoriteMovies.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }
    
    val filteredOtherMovies = remember(otherMovies, searchQuery) {
        if (searchQuery.isEmpty()) otherMovies
        else otherMovies.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }
    
    // Check if all lists are empty
    val allListsEmpty = filteredWatchLater.isEmpty() && 
                        filteredGroupFavorites.isEmpty() && 
                        filteredOtherMovies.isEmpty()
    
    // Log for debugging
    LaunchedEffect(selectedMovies) {
        Log.d("MovieSelectionStep", "Selected movies: ${selectedMovies.size}")
    }
    
    // Use Box as the root container to allow proper layering
    Box(modifier = Modifier.fillMaxSize()) {
        // Main scrollable content
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp, bottom = 80.dp)
        ) {
//             Search bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = MovieSelectionModifiers.searchBar
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            // Watch Later section
            HorizontalMovieSection(
                title = "Watch Later",
                movies = filteredWatchLater,
                selectedMovies = selectedMovies,
                onToggleSelection = onToggleMovieSelection
            )
            
            if (filteredWatchLater.isNotEmpty()) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
            
            // Group Favorites section
            HorizontalMovieSection(
                title = "Group Favorites",
                movies = filteredGroupFavorites,
                selectedMovies = selectedMovies,
                onToggleSelection = onToggleMovieSelection
            )
            
            if (filteredGroupFavorites.isNotEmpty()) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
            
            // Other Movies section
            GridMovieSection(
                title = "Other Movies",
                movies = filteredOtherMovies,
                selectedMovies = selectedMovies,
                onToggleSelection = onToggleMovieSelection
            )
            
            // Empty state
            if (allListsEmpty && searchQuery.isNotEmpty()) {
                EmptySearchResults(
                    query = searchQuery,
                    modifier = MovieSelectionModifiers.emptyResults
                )
            }
        }
        
        // Persistent bottom bar - now it will be positioned at the bottom
        PersistentBottomBar(
            selectedCount = selectedMovies.size,
            onCreatePollClick = { showReviewSheet = true },
            modifier = Modifier
        )
    }
    
    // Review bottom sheet
    if (showReviewSheet) {
        ReviewBottomSheet(
            selectedMovies = selectedMovies.toList(),
            onRemoveMovie = { onToggleMovieSelection(it) },
            onCreatePoll = onCreatePoll,
            onDismiss = { showReviewSheet = false }
        )
    }
} 