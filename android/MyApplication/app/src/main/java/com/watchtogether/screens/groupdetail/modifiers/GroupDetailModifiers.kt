package com.watchtogether.screens.groupdetail.modifiers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Modifiers specific to the Group Detail feature
 */
object GroupDetailModifiers {
    /**
     * Section title
     */
    fun sectionTitle() = Modifier
        .padding(vertical = 8.dp)
    
    /**
     * Member item container
     */
    fun memberItem() = Modifier
        .width(80.dp)
    
    /**
     * Member avatar
     */
    fun memberAvatar() = Modifier
        .size(60.dp)
    
    /**
     * Member icon
     */
    fun memberIcon() = Modifier
        .padding(16.dp)
        .size(28.dp)
    
    /**
     * Poll item
     */
    fun pollItem() = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
    
    /**
     * Poll icon
     */
    fun pollIcon() = Modifier
        .size(24.dp)
} 