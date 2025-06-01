package com.watchtogether.data.repositories

import android.util.Log
import com.watchtogether.models.Group
import com.watchtogether.models.GroupMember
import com.watchtogether.models.MemberRole
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class GroupRepository(
    private val supabase: SupabaseClient
) {
    
    
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
    
    suspend fun createGroup(name: String, description: String? = null): Result<Group> = withContext(Dispatchers.IO) {
        try {
            // Get the current authenticated user ID
            val currentUser = supabase.auth.currentUserOrNull()
            if (currentUser == null) {
                Log.e("GroupRepository", "No authenticated user found")
                return@withContext Result.failure(Exception("User not authenticated"))
            }
            
            val payload = buildJsonObject {
                put("name", name)
                put("created_by", currentUser.id) // Add the created_by field
                if (description != null) {
                    put("description", description)
                }
            }
            
            Log.d("GroupRepository", "Creating group with payload: $payload")
            
            val newGroup = supabase.from("groups")
                .insert(payload) {
                    select() // This tells Supabase to return the inserted data
                }
                .decodeSingle<Group>()
            
            Log.d("GroupRepository", "Successfully created group: ${newGroup}")
            Result.success(newGroup)
        } catch (e: Exception) {
            Log.e("GroupRepository", "Error creating group", e)
            Result.failure(e)
        }
    }
    
    suspend fun getGroupMembers(groupId: Int): Result<List<GroupMember>> = withContext(Dispatchers.IO) {
        try {
            val members = supabase.from("group_members")
                .select {
                    filter {
                        eq("group_id", groupId)
                    }
                }
                .decodeList<GroupMember>()
            
            Result.success(members)
        } catch (e: Exception) {
            Log.e("GroupRepository", "Error fetching members for group $groupId", e)
            Result.failure(e)
        }
    }
    
    suspend fun joinGroup(groupId: Int, role: MemberRole = MemberRole.MEMBER): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // Get the current authenticated user ID
            val currentUser = supabase.auth.currentUserOrNull()
            if (currentUser == null) {
                Log.e("GroupRepository", "No authenticated user found")
                return@withContext Result.failure(Exception("User not authenticated"))
            }
            
            supabase.from("group_members")
                .insert(
                    buildJsonObject {
                        put("group_id", groupId)
                        put("user_id", currentUser.id) // Add the user_id field
                        put("role", role.name.lowercase())
                    }
                )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("GroupRepository", "Error joining group $groupId", e)
            Result.failure(e)
        }
    }
} 