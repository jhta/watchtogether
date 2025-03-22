package com.watchtogether.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String = "",
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (title.isNotEmpty()) {
                // Show regular AppTopBar with title and optional back button
                AppTopBar(
                    title = title,
                    showBackButton = showBackButton,
                    onBackClick = onBackClick
                )
            } else {
                // Show empty TopAppBar (just for structure, no content)
                EmptyTopAppBar()
            }
        },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmptyTopAppBar() {
    TopAppBar(
        title = { /* Empty title */ },
        navigationIcon = { /* No navigation icon */ }
    )
}