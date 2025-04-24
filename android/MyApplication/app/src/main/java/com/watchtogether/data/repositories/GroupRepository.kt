package com.watchtogether.data.repositories

import android.util.Log
import com.watchtogether.models.Group
import com.watchtogether.supabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GroupRepository {
    
    suspend fun getGroups(): Result<List<Group>> = withContext(Dispatchers.IO) {
        try {
            val groups = supabase.from("groups")
                .select()
                .decodeList<Group>()
            Result.success(groups)
        } catch (e: Exception) {
            Log.e("GroupRepository", "Error fetching groups", e)
            Result.failure(e)
        }
    }
    
    suspend fun getGroupById(groupId: Int): Result<Group> = withContext(Dispatchers.IO) {
        try {
            val group = supabase.from("groups")
                .select() {
                    filter {
                        eq("id", groupId)
                    }
                }
                .decodeSingle<Group>()
            Result.success(group)
        } catch (e: Exception) {
            Log.e("GroupRepository", "Error fetching group with id $groupId", e)
            Result.failure(e)
        }
    }
    
    suspend fun createGroup(name: String): Result<Group> = withContext(Dispatchers.IO) {
        try {
            val newGroup = supabase.from("groups")
                .insert(mapOf("name" to name))
                .decodeSingle<Group>()
            Result.success(newGroup)
        } catch (e: Exception) {
            Log.e("GroupRepository", "Error creating group", e)
            Result.failure(e)
        }
    }
} 