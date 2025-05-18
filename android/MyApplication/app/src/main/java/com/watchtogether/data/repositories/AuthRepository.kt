package com.watchtogether.data.repositories

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val supabase: SupabaseClient
) {
    
    /**
     * Sign in with Google OAuth
     */
    suspend fun signInWithGoogle(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            supabase.auth.signInWith(Google)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error signing in with Google", e)
            Result.failure(e)
        }
    }
    
    /**
     * Sign out the current user
     */
    suspend fun signOut(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            supabase.auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error signing out", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get the current authenticated user, or null if not logged in
     */
    fun getCurrentUser(): UserInfo? {
        return supabase.auth.currentUserOrNull()
    }
    
    /**
     * Check if the user is logged in
     */
    fun isLoggedIn(): Boolean {
        return supabase.auth.currentSessionOrNull() != null
    }
} 