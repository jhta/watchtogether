package com.watchtogether

import android.app.Application
import com.watchtogether.di.appModule
import com.watchtogether.utils.Environment
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

// Lazily initialize Supabase client
lateinit var supabase: io.github.jan.supabase.SupabaseClient

class WatchTogetherApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Supabase with environment variables and application context
        supabase = createSupabaseClient(
            supabaseUrl = Environment.getSupabaseUrl(this),
            supabaseKey = Environment.getSupabaseKey(this)
        ) {
            install(Postgrest)
        }
        
        // Initialize Koin for dependency injection
        startKoin {
            androidContext(this@WatchTogetherApp)
            modules(appModule)
        }
    }
} 