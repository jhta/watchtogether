package com.watchtogether.screens.createpoll

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.watchtogether.components.AppScaffold
import com.watchtogether.models.Movie
import com.watchtogether.screens.createpoll.components.MovieSelectionStep.MovieSelectionStep
import com.watchtogether.screens.createpoll.components.PollTitleStep
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreatePollScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // State for the current step
    val currentStep = remember { mutableStateOf(CreatePollStep.ENTER_TITLE) }
    
    // State for poll title
    val pollTitle = remember { mutableStateOf("") }
    

    // State for selected movies
    var selectedMovies by remember { mutableStateOf(setOf<Movie>()) }
    
    // Mock data for movies
    val watchLaterMovies = remember {
        listOf(
            Movie("1", "The Shawshank Redemption", "1994", rating = 9.3f),
            Movie("2", "The Godfather", "1972", rating = 9.2f),
            Movie("3", "The Dark Knight", "2008", rating = 9.0f),
            Movie("4", "Pulp Fiction", "1994", rating = 8.9f)
        )
    }
    
    val groupFavoriteMovies = remember {
        listOf(
            Movie("5", "Inception", "2010", rating = 8.8f),
            Movie("6", "Fight Club", "1999", rating = 8.8f),
            Movie("7", "Forrest Gump", "1994", rating = 8.8f)
        )
    }
    
    val otherMovies = remember {
        listOf(
            Movie("8", "The Matrix", "1999", rating = 8.7f),
            Movie("9", "Goodfellas", "1990", rating = 8.7f),
            Movie("10", "Interstellar", "2014", rating = 8.6f),
            Movie("11", "The Silence of the Lambs", "1991", rating = 8.6f),
            Movie("12", "Saving Private Ryan", "1998", rating = 8.6f),
            Movie("13", "The Green Mile", "1999", rating = 8.6f),
            Movie("14", "Star Wars: Episode IV", "1977", rating = 8.6f),
            Movie("15", "Terminator 2", "1991", rating = 8.5f)
        )
    }
    
    // Get list of selected movies
    val selectedMovieIds = remember(selectedMovies) {
        selectedMovies.map { it.id }.toSet()
    }
    
    // Determine app bar title based on current step
    val appBarTitle = when (currentStep.value) {
        CreatePollStep.ENTER_TITLE -> "Create Poll"
        CreatePollStep.SELECT_MOVIES -> "Select Movies"
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(appBarTitle) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        AnimatedContent(
            targetState = currentStep.value,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) with
                fadeOut(animationSpec = tween(300))
            },
            modifier = Modifier.padding(innerPadding)
        ) { step ->
            when (step) {
                CreatePollStep.ENTER_TITLE -> {
                    PollTitleStep(
                        pollTitle = pollTitle.value,
                        onPollTitleChange = { pollTitle.value = it },
                        onNextStep = { 
                            if (pollTitle.value.isNotEmpty()) {
                                currentStep.value = CreatePollStep.SELECT_MOVIES
                            }
                        }
                    )
                }
                CreatePollStep.SELECT_MOVIES -> {
                    MovieSelectionStep(
                        watchLaterMovies = watchLaterMovies,
                        groupFavoriteMovies = groupFavoriteMovies,
                        otherMovies = otherMovies,
                        onToggleMovieSelection = { movie ->
                            selectedMovies = if (movie in selectedMovies) {
                                selectedMovies - movie
                            } else {
                                selectedMovies + movie
                            }
                        },
                        selectedMovies = selectedMovies,
                        onCreatePoll = {
                            if (pollTitle.value.isNotEmpty() && selectedMovies.isNotEmpty()) {
                                // Handle poll creation
                                // In a real app, this would save the poll and navigate
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
} 