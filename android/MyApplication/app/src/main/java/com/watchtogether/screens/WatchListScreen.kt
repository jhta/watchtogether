package com.watchtogether.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.unit.sp
import com.watchtogether.components.GridMovieSection
import com.watchtogether.components.HorizontalMovieSection
import com.watchtogether.models.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchListScreen(
    modifier: Modifier = Modifier
) {
    // Mock data
    val favoriteMovies = remember {
        listOf(
            Movie(
                id = "1",
                title = "Inception",
                year = "2010",
                posterUrl = "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
                rating = 8.8f,
                description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O."
            ),
            Movie(
                id = "2",
                title = "The Dark Knight",
                year = "2008",
                posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                rating = 9.0f,
                description = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice."
            ),
            Movie(
                id = "3",
                title = "Interstellar",
                year = "2014",
                posterUrl = "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
                rating = 8.6f,
                description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival."
            )
        )
    }

    val otherMovies = remember {
        listOf(
            Movie(
                id = "4",
                title = "The Matrix",
                year = "1999",
                posterUrl = "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg",
                rating = 8.7f,
                description = "A computer programmer discovers that reality as he knows it is a simulation created by machines, and joins a rebellion to break free."
            ),
            Movie(
                id = "5",
                title = "Pulp Fiction",
                year = "1994",
                posterUrl = "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg",
                rating = 8.9f,
                description = "Various interconnected stories of criminals in Los Angeles."
            ),
            Movie(
                id = "6",
                title = "Fight Club",
                year = "1999",
                posterUrl = "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
                rating = 8.4f,
                description = "An insomniac office worker and a devil-may-care soapmaker form an underground fight club."
            ),
            Movie(
                id = "7",
                title = "Forrest Gump",
                year = "1994",
                posterUrl = "https://image.tmdb.org/t/p/w500/saHP97rTPS5eLmrLQEcANmKrsFl.jpg",
                rating = 8.8f,
                description = "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75."
            ),
            Movie(
                id = "8",
                title = "The Shawshank Redemption",
                year = "1994",
                posterUrl = "https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
                rating = 9.3f,
                description = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."
            )
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedMovies by remember { mutableStateOf(setOf<Movie>()) }
    
    // Filter movies based on search query
    val filteredFavorites = favoriteMovies.filter { movie ->
        movie.title.contains(searchQuery, ignoreCase = true)
    }
    
    val filteredWatchlist = otherMovies.filter { movie ->
        movie.title.contains(searchQuery, ignoreCase = true)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        item {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { },
                active = false,
                onActiveChange = { },
                placeholder = { Text("Search movies...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) { }
        }

        item {
            HorizontalMovieSection(
                title = "Favorites",
                movies = filteredFavorites,
                selectedMovies = selectedMovies,
                onToggleSelection = { movie ->
                    selectedMovies = if (selectedMovies.contains(movie)) {
                        selectedMovies - movie
                    } else {
                        selectedMovies + movie
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        if (filteredFavorites.isNotEmpty()) {
            item {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }

        item {
            GridMovieSection(
                title = "All Movies",
                movies = filteredWatchlist,
                selectedMovies = selectedMovies,
                onToggleSelection = { movie ->
                    selectedMovies = if (selectedMovies.contains(movie)) {
                        selectedMovies - movie
                    } else {
                        selectedMovies + movie
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
} 