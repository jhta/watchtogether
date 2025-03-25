package com.watchtogether.screens.groupdetail

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.watchtogether.supabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

@Composable
fun GroupDetailScreen(
    groupId: Int,
    onBackClick: () -> Unit = {},
    onCreatePollClick: (Int) -> Unit = {},
    onPollClick: (String) -> Unit = {}
) {
    // State for polls
    var polls by remember { mutableStateOf<List<Poll>>(emptyList()) }

    // Fetch polls from Supabase
    LaunchedEffect(groupId) {
        try {
            withContext(Dispatchers.IO) {
                val fetchedPolls = supabase
                    .from("polls")
                    .select() {
                        filter {
                            eq("group_id", groupId)

                        }
                        order("created_at", order = Order.DESCENDING)
                    }
                    .decodeList<Poll>()
                
                Log.d("GroupDetailScreen", "Fetched polls: $fetchedPolls")
                polls = fetchedPolls
            }
        } catch (e: Exception) {
            Log.e("GroupDetailScreen", "Error fetching polls", e)
        }
    }

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
                        activePolls = polls.filter { it.status == PollStatus.ACTIVE },
                        onPollClick = { poll -> onPollClick(poll.id) }
                    )
                }
                
                // Completed Polls Section
                item {
                    CompletedPollsSection(
                        completedPolls = polls.filter { it.status == PollStatus.CLOSED},
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