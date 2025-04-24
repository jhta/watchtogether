package com.watchtogether.utils

import android.content.Context
import android.util.Log
import com.watchtogether.BuildConfig
import io.github.cdimascio.dotenv.dotenv
import java.io.File
import java.util.Properties

object Environment {
    // Default values as fallback (only use for non-sensitive defaults)
    private const val DEFAULT_SUPABASE_URL = ""
    private const val DEFAULT_SUPABASE_KEY = ""
    
    private val envProperties = java.util.Properties()
    private var isInitialized = false
    
    // Load environment variables from assets folder
    private fun initializeEnv(context: Context) {
        if (isInitialized) return
        
        try {
            // Try loading from assets/env/.env first
            context.assets.open("env/.env").use { inputStream ->
                envProperties.load(inputStream)
                isInitialized = true
                Log.d("Environment", "Loaded env from assets")
                return
            }
        } catch (e: Exception) {
            Log.d("Environment", "Could not load .env from assets: ${e.message}")
        }
        
        try {
            // Fallback to dotenv library (project root)
            val env = dotenv {
                directory = "./android/MyApplication/"
                filename = ".env"
            }
            
            for (entry in env.entries()) {
                val key = entry.key
                val value = entry.value
                envProperties.setProperty(key, value)
            }
            
            isInitialized = true
            Log.d("Environment", "Loaded env from project root")
        } catch (e: Exception) {
            Log.e("Environment", "Could not load .env file: ${e.message}")
        }
    }
    
    // Get from build config if added there
    private fun getFromBuildConfig(key: String): String? {
        return when (key) {
            "SUPABASE_URL" -> BuildConfig.SUPABASE_URL.takeIf { it.isNotEmpty() }
            "SUPABASE_KEY" -> BuildConfig.SUPABASE_KEY.takeIf { it.isNotEmpty() }
            else -> null
        }
    }
    
    // Get from app resources
    private fun getFromResources(context: Context, key: String): String? {
        return try {
            val resourceId = context.resources.getIdentifier(
                key.lowercase(), "string", context.packageName
            )
            if (resourceId != 0) context.getString(resourceId) else null
        } catch (e: Exception) {
            null
        }
    }
    
    // Get environment variable with fallbacks
    fun get(key: String, context: Context? = null): String? {
        // Try BuildConfig first (from local.properties/gradle)
        getFromBuildConfig(key)?.let { return it }
        
        // Then try system environment variables (useful for CI/CD)
        System.getenv(key)?.let { return it }
        
        // Initialize and check file-based environment variables
        context?.let { initializeEnv(it) }
        envProperties.getProperty(key)?.let { return it }
        
        // Last resort, check resources
        context?.let { getFromResources(it, key) }?.let { return it }
        
        return null
    }
    
    // Specific getters for Supabase credentials
    fun getSupabaseUrl(context: Context? = null): String {
        return get("SUPABASE_URL", context) ?: DEFAULT_SUPABASE_URL
    }
    
    fun getSupabaseKey(context: Context? = null): String {
        return get("SUPABASE_KEY", context) ?: DEFAULT_SUPABASE_KEY
    }
} 