package com.watchtogether.screens.createpoll.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.watchtogether.screens.createpoll.modifiers.CreatePollModifiers

@Composable
fun MovieSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text("Search for movies...") },
        leadingIcon = { 
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        singleLine = true,
        modifier = modifier.then(CreatePollModifiers.searchBar())
    )
} 