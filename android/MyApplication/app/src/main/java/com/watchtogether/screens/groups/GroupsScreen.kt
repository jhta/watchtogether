package com.watchtogether.screens.groups

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.watchtogether.models.Group
import com.watchtogether.screens.groups.components.GroupCard
import com.watchtogether.screens.groups.modifiers.GroupsModifiers
import com.watchtogether.ui.viewmodels.GroupsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroupsScreen(
    modifier: Modifier = Modifier,
    onCreateGroupClick: () -> Unit = {},
    onGroupClick: (Int) -> Unit = {},
    shouldRefresh: Boolean = false,
    viewModel: GroupsViewModel = koinViewModel()
) {
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // State for selected group and dialog visibility
    val selectedGroup = remember { mutableStateOf<Group?>(null) }
    
    // Refresh groups when shouldRefresh changes to true
    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh) {
            viewModel.refreshGroups()
        }
    }
    
    Box(
        modifier = GroupsModifiers.rootContainer
            .then(modifier)
    ) {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading groups...")
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(uiState.error ?: "Unknown error occurred")
                }
            }
            else -> {
                // List of group cards
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    modifier = GroupsModifiers.groupList
                ) {
                    items(uiState.groups) { group ->
                        GroupCard(
                            group = group,
                            onClick = { onGroupClick(group.id) }
                        )
                    }
                    
                    // Add some space at the bottom for the FAB
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
        
        // Floating Action Button to add a new group
        FloatingActionButton(
            onClick = onCreateGroupClick,
            modifier = GroupsModifiers.fab
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Group"
            )
        }
        
        // Dialog for showing group details when a card is clicked
        if (selectedGroup.value != null) {
            AlertDialog(
                onDismissRequest = { selectedGroup.value = null },
                title = { Text("Group Details") },
                text = { 
                    Text("You selected: ${selectedGroup.value?.name}")
                },
                confirmButton = {
                    TextButton(onClick = { selectedGroup.value = null }) {
                        Text("Close")
                    }
                }
            )
        }
    }
} 