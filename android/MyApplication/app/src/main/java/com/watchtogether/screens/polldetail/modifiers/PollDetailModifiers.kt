package com.watchtogether.screens.polldetail.modifiers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object PollDetailModifiers {
    val rootContainer = Modifier
        .fillMaxSize()
    
    val contentContainer = Modifier
        .fillMaxSize()
        .padding(16.dp)
    
    val pollTitle = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp)
    
    val pollStatus = Modifier
        .fillMaxWidth()
        .padding(bottom = 24.dp)
    
    val movieList = Modifier
        .fillMaxWidth()
    
    val voteCount = Modifier
        .padding(start = 8.dp)
} 