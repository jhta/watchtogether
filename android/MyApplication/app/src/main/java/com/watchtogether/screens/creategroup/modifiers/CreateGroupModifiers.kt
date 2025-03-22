package com.watchtogether.screens.creategroup.modifiers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Modifiers specific to the Create Group feature
 */
object CreateGroupModifiers {
    /**
     * Group name title
     */
    fun title() = Modifier
        .padding(bottom = 16.dp)
    
    /**
     * Create group button
     */
    fun createButton() = Modifier
        .fillMaxWidth()
    
    /**
     * Invitation code card
     */
    fun invitationCard() = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    
    /**
     * Content inside invitation card
     */
    fun invitationCardContent() = Modifier
        .padding(16.dp)
    
    /**
     * Invitation code text
     */
    fun invitationCodeText() = Modifier
} 