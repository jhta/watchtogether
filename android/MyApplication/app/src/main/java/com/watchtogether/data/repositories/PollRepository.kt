package com.watchtogether.data.repositories

import android.util.Log
import com.watchtogether.models.Poll
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PollRepository(
    private val supabase: SupabaseClient
) {
    
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
} 