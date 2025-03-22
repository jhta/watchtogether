package com.watchtogether.screens.createpoll.components.MovieSelectionStep.modifiers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object MovieSelectionModifiers {
    val rootContainer = Modifier
        .fillMaxSize()
    
    val searchBar = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    
    val sectionHeader = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
    
    val sectionContent = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    
    val emptyResults = Modifier
        .fillMaxWidth()
        .padding(32.dp)
} 