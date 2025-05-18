package com.watchtogether

import android.app.Application
import com.watchtogether.data.SupabaseManager
import com.watchtogether.di.appModule
import io.github.jan.supabase.SupabaseClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

// Lazily initialize Supabase client
lateinit var supabase: SupabaseClient

class WatchTogetherApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Supabase client using the SupabaseManager
        supabase = SupabaseManager.getClient(this)
        
        // Initialize Koin for dependency injection
        startKoin {
            androidContext(this@WatchTogetherApp)
            modules(appModule)
        }
    }
} 