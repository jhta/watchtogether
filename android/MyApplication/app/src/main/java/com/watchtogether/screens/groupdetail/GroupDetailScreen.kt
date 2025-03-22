package com.watchtogether.screens.groupdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.watchtogether.components.AppScaffold
import com.watchtogether.models.Group
import com.watchtogether.models.Member
import com.watchtogether.models.Poll
import com.watchtogether.models.PollStatus
import com.watchtogether.screens.groupdetail.components.ActivePollsSection
import com.watchtogether.screens.groupdetail.components.CompletedPollsSection
import com.watchtogether.screens.groupdetail.components.GroupInfoSection
import com.watchtogether.screens.groupdetail.components.MembersSection
import java.util.Date

@Composable
fun GroupDetailScreen(
    groupId: String,
    onBackClick: () -> Unit = {},
    onCreatePollClick: (String) -> Unit = {},
    onPollClick: (String) -> Unit = {}
) {
    // In a real app, you would fetch the group details based on the groupId
    // For now, we'll create mock data
    val group = remember {
        Group(
            id = groupId,
            name = "Movie Night Club",
            participantCount = 8
        )
    }
    
    val members = remember {
        listOf(
            Member("1", "John Doe"),
            Member("2", "Jane Smith"),
            Member("3", "Robert Johnson"),
            Member("4", "Emily Davis"),
            Member("5", "Michael Brown"),
            Member("6", "Sarah Wilson"),
            Member("7", "David Taylor"),
            Member("8", "Lisa Anderson")
        )
    }
    
    val activePolls = remember {
        listOf(
            Poll(
                id = "1",
                title = "Weekend Movie Night",
                description = "Vote for this weekend's movie!",
                status = PollStatus.ACTIVE,
                createdAt = Date(),
                endDate = Date(System.currentTimeMillis() + 86400000) // Tomorrow
            ),
            Poll(
                id = "2",
                title = "Next Week's Marathon",
                description = "Choose a movie series for next week",
                status = PollStatus.ACTIVE,
                createdAt = Date(),
                endDate = Date(System.currentTimeMillis() + 172800000) // Day after tomorrow
            )
        )
    }
    
    val completedPolls = remember {
        listOf(
            Poll(
                id = "3",
                title = "Last Week's Movie",
                description = "The Shawshank Redemption won!",
                status = PollStatus.COMPLETED,
                createdAt = Date(System.currentTimeMillis() - 604800000), // A week ago
                endDate = Date(System.currentTimeMillis() - 518400000) // 6 days ago
            ),
            Poll(
                id = "4",
                title = "Snack Preferences",
                description = "Popcorn and nachos were the winners!",
                status = PollStatus.COMPLETED,
                createdAt = Date(System.currentTimeMillis() - 1209600000), // Two weeks ago
                endDate = Date(System.currentTimeMillis() - 1123200000) // 13 days ago
            )
        )
    }
    
    AppScaffold(
        title = group.name,
        showBackButton = true,
        onBackClick = onBackClick
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                // Group Info Section
                item {
                    GroupInfoSection(group = group)
                }
                
                // Members Section
                item {
                    MembersSection(members = members)
                }
                
                // Active Polls Section
                item {
                    ActivePollsSection(
                        activePolls = activePolls,
                        onPollClick = { poll -> onPollClick(poll.id) }
                    )
                }
                
                // Completed Polls Section
                item {
                    CompletedPollsSection(
                        completedPolls = completedPolls,
                        onPollClick = { poll -> onPollClick(poll.id) }
                    )
                }
            }
            
            // Floating Action Button to create a new poll
            FloatingActionButton(
                onClick = { onCreatePollClick(groupId) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create New Poll"
                )
            }
        }
    }
} 