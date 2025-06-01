package com.watchtogether.data.repositories

import android.util.Log
import com.watchtogether.models.Poll
import com.watchtogether.models.PollOption
import com.watchtogether.models.PollStatus
import com.watchtogether.models.Vote
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray

class PollRepository(
    private val supabase: SupabaseClient
) {
    // Schema for all queries
    
    suspend fun getPollsByGroupId(groupId: Int): Result<List<Poll>> = withContext(Dispatchers.IO) {
        try {
            val polls = supabase
                .from("polls")
                .select() {
                    filter {
                        eq("group_id", groupId)
                    }
                    order("created_at", order = Order.DESCENDING)
                }
                .decodeList<Poll>()
            
            Log.d("PollRepository", "Fetched polls: $polls")
            Result.success(polls)
        } catch (e: Exception) {
            Log.e("PollRepository", "Error fetching polls for group $groupId", e)
            Result.failure(e)
        }
    }
    
    suspend fun getPollById(pollId: Int): Result<Poll> = withContext(Dispatchers.IO) {
        try {
            val poll = supabase
                .from("polls")
                .select() {
                    filter {
                        eq("id", pollId)
                    }
                }
                .decodeSingle<Poll>()
            
            Result.success(poll)
        } catch (e: Exception) {
            Log.e("PollRepository", "Error fetching poll $pollId", e)
            Result.failure(e)
        }
    }
    
    suspend fun createPoll(
        groupId: Int, 
        title: String, 
        description: String? = null,
        endsAt: String? = null
    ): Result<Poll> = withContext(Dispatchers.IO) {
        try {
            val payload = buildJsonObject {
                put("group_id", groupId)
                put("title", title)
                put("status", PollStatus.ACTIVE.name.lowercase())
                if (description != null) {
                    put("description", description)
                }
                if (endsAt != null) {
                    put("ends_at", endsAt)
                }
            }
            
            val newPoll = supabase
                .from("polls")
                .insert(payload)
                .decodeSingle<Poll>()
            
            Result.success(newPoll)
        } catch (e: Exception) {
            Log.e("PollRepository", "Error creating poll", e)
            Result.failure(e)
        }
    }
    
    suspend fun getPollOptions(pollId: Int): Result<List<PollOption>> = withContext(Dispatchers.IO) {
        try {
            val options = supabase
                .from("poll_options")
                .select() {
                    filter {
                        eq("poll_id", pollId)
                    }
                }
                .decodeList<PollOption>()
            
            Result.success(options)
        } catch (e: Exception) {
            Log.e("PollRepository", "Error fetching options for poll $pollId", e)
            Result.failure(e)
        }
    }
    
    suspend fun addPollOption(
        pollId: Int,
        movieTitle: String,
        movieId: String? = null
    ): Result<PollOption> = withContext(Dispatchers.IO) {
        try {
            val payload = buildJsonObject {
                put("poll_id", pollId)
                put("movie_title", movieTitle)
                if (movieId != null) {
                    put("movie_id", movieId)
                }
            }
            
            val newOption = supabase
                .from("poll_options")
                .insert(payload)
                .decodeSingle<PollOption>()
            
            Result.success(newOption)
        } catch (e: Exception) {
            Log.e("PollRepository", "Error adding option to poll $pollId", e)
            Result.failure(e)
        }
    }
    
    suspend fun voteForOption(pollId: Int, optionId: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val payload = buildJsonObject {
                put("poll_id", pollId)
                put("option_id", optionId)
            }
            
            supabase
                .from("votes")
                .insert(payload)
            
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("PollRepository", "Error voting for option $optionId in poll $pollId", e)
            Result.failure(e)
        }
    }
//
//    suspend fun updatePollStatus(pollId: Int, status: PollStatus): Result<Unit> = withContext(Dispatchers.IO) {
//        try {
//            supabase
//                .from("polls")
//                .update(
//                    {
//                        put("status", status.name.lowercase())
//                    }
//                ) {
//                    filter {
//                        eq("id", pollId)
//                    }
//                }
//
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Log.e("PollRepository", "Error updating poll status", e)
//            Result.failure(e)
//        }
//    }
} 