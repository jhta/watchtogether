package com.watchtogether.screens.createpoll.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.watchtogether.models.MovieSource
import com.watchtogether.screens.createpoll.modifiers.CreatePollModifiers

@Composable
fun MovieSourceSelector(
    selectedSource: MovieSource,
    onSourceSelected: (MovieSource) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.then(CreatePollModifiers.sourceSelector())
    ) {
        Text(
            text = "Select Movie Source",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        MovieSourceOption(
            source = MovieSource.WATCH_LATER,
            selected = selectedSource == MovieSource.WATCH_LATER,
            onSelect = { onSourceSelected(MovieSource.WATCH_LATER) }
        )
        
        MovieSourceOption(
            source = MovieSource.GROUP_FAVORITES,
            selected = selectedSource == MovieSource.GROUP_FAVORITES,
            onSelect = { onSourceSelected(MovieSource.GROUP_FAVORITES) }
        )
        
        MovieSourceOption(
            source = MovieSource.SEARCH_RESULTS,
            selected = selectedSource == MovieSource.SEARCH_RESULTS,
            onSelect = { onSourceSelected(MovieSource.SEARCH_RESULTS) }
        )
    }
}

@Composable
private fun MovieSourceOption(
    source: MovieSource,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onSelect
            )
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect
        )
        
        Text(
            text = when(source) {
                MovieSource.WATCH_LATER -> "My Watch Later List"
                MovieSource.GROUP_FAVORITES -> "Group Favorites"
                MovieSource.SEARCH_RESULTS -> "Search for Movies"
            },
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
} 