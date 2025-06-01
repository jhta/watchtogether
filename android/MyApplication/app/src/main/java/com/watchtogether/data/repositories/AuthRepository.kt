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
     * Modern Supabase-kt automatically handles session loading from storage
     */
    suspend fun getCurrentUser(): UserInfo? = withContext(Dispatchers.IO) {
        try {
            val session = supabase.auth.currentSessionOrNull()
            if (session != null) {
                Log.d("AuthRepository", "Found valid session for user: ${session.user?.id}")
                return@withContext session.user
            }
            
            Log.d("AuthRepository", "No current session found")
            return@withContext null
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error getting current user", e)
            null
        }
    }
    
    /**
     * Check if the user is logged in
     * Modern Supabase-kt automatically handles session validation and refresh
     */
    suspend fun isLoggedIn(): Boolean = withContext(Dispatchers.IO) {
        try {
            val session = supabase.auth.currentSessionOrNull()
            val isValid = session != null
            
            Log.d("AuthRepository", "Session check - Valid: $isValid, Session exists: ${session != null}")
            return@withContext isValid
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error checking login status", e)
            false
        }
    }
    
    /**
     * Force refresh the current session from storage
     * This can help in cases where session state might be stale
     */
    suspend fun refreshSession(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            // In modern Supabase-kt, sessions are automatically loaded and managed
            // We can simply check the current session state
            val session = supabase.auth.currentSessionOrNull()
            val isLoggedIn = session != null
            
            Log.d("AuthRepository", "Session check completed. Logged in: $isLoggedIn")
            Result.success(isLoggedIn)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error checking session", e)
            Result.failure(e)
        }
    }
} 