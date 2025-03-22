package com.watchtogether.screens.createpoll.modifiers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object CreatePollModifiers {
    /**
     * Screen title
     */
    fun screenTitle() = Modifier
        .padding(bottom = 16.dp)
    
    /**
     * Poll title input
     */
    fun pollTitleInput() = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp)
    
    /**
     * Source selector container
     */
    fun sourceSelector() = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    
    /**
     * Search bar
     */
    fun searchBar() = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    
    /**
     * Movie list container
     */
    fun movieList() = Modifier
        .fillMaxWidth()
    
    /**
     * Movie item
     */
    fun movieItem() = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)
    
    /**
     * Movie poster
     */
    fun moviePoster() = Modifier
        .size(60.dp)
    
    /**
     * Selected movies section
     */
    fun selectedMoviesSection() = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
    
    /**
     * Create poll button
     */
    fun createPollButton() = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
} 