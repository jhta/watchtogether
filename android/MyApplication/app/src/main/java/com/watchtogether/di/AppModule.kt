package com.watchtogether.di

import com.watchtogether.data.SupabaseManager
import com.watchtogether.data.repositories.AuthRepository
import com.watchtogether.data.repositories.GroupRepository
import com.watchtogether.data.repositories.PollRepository
import com.watchtogether.ui.viewmodels.AuthViewModel
import com.watchtogether.ui.viewmodels.CreateGroupViewModel
import com.watchtogether.ui.viewmodels.GroupDetailViewModel
import com.watchtogether.ui.viewmodels.GroupsViewModel
import io.github.jan.supabase.SupabaseClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Services
    single<SupabaseClient> { SupabaseManager.getClient(get()) }
    
    // Repositories
    single { GroupRepository(get()) }
    single { PollRepository(get()) }
    single { AuthRepository(get()) }
    
    // ViewModels
    viewModel { GroupsViewModel(get()) }
    viewModel { GroupDetailViewModel(get()) }
    viewModel { CreateGroupViewModel(get()) }
    viewModel { AuthViewModel(get()) }
} 