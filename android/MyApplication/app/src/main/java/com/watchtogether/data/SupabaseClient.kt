package com.watchtogether.data

import android.content.Context
import com.watchtogether.utils.Environment
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

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
        return createSupabaseClient(
            supabaseUrl = Environment.getSupabaseUrl(context),
            supabaseKey = Environment.getSupabaseKey(context)
        ) {
            install(Postgrest)
            // Add other plugins as needed
        }
    }
} 