package com.watchtogether.ui.modifiers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Common modifiers used across multiple features
 */
object CommonModifiers {
    /**
     * Standard screen container with padding
     */
    fun screenContainer(innerPadding: PaddingValues) = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(16.dp)
    
    /**
     * Full width element
     */
    fun fullWidth() = Modifier
        .fillMaxWidth()
    
    /**
     * Standard input field
     */
    fun inputField() = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    
    /**
     * Standard button
     */
    fun button() = Modifier
        .fillMaxWidth()
} 