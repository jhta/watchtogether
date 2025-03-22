package com.watchtogether.screens.groups.modifiers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

object GroupsModifiers {
    val rootContainer = Modifier
        .fillMaxSize()
    
    val groupList = Modifier
        .fillMaxSize()
        .padding(16.dp)
    
    val groupCard = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)
    
    val groupCardContent = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    
    val groupIcon = Modifier
        .size(40.dp)

    val fab = Modifier
        .padding(16.dp)
} 