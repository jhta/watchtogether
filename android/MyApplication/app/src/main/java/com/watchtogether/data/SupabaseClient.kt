package com.watchtogether.data

import android.content.Context
import android.util.Log
import com.watchtogether.utils.Environment
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.FlowType

/**
 * Singleton class that manages the Supabase client instance.
 */
object SupabaseManager {
    private var instance: SupabaseClient? = null
    
    /**
     * Returns the Supabase client instance, initializing it if needed.
     * 
     * @param context Application context for accessing environment variables.
     * @return The SupabaseClient instance.
     */
    fun getClient(context: Context): SupabaseClient {
        return instance ?: synchronized(this) {
            instance ?: createClient(context).also { instance = it }
        }
    }
    
    /**
     * Creates a new Supabase client with the configured settings.
     * 
     * @param context Application context for accessing environment variables.
     * @return A new SupabaseClient instance.
     */
    private fun createClient(context: Context): SupabaseClient {
        val supabaseUrl = Environment.getSupabaseUrl(context)
        val supabaseKey = Environment.getSupabaseKey(context)
        
        Log.d("SupabaseManager", "Creating client with URL: $supabaseUrl")
        
        return createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest) {
                defaultSchema = "movie_namespace"  // Set the default schema for all queries
            }
            install(Auth) {
                // Configure for deep linking with OAuth providers
                scheme = "com.watchtogether"
                host = "login-callback"
                flowType = FlowType.PKCE  // Use PKCE flow for better mobile security
                
                // CRITICAL: Enable session persistence - sessions are automatically persisted by default
                // The Auth plugin automatically handles session storage in encrypted SharedPreferences
                // No additional configuration needed for session persistence
                
                // Enable debug logs for auth
                // debug = true
            }
            // Add other plugins as needed
        }
    }
} 