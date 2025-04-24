package com.watchtogether.di

import com.watchtogether.data.repositories.GroupRepository
import com.watchtogether.ui.viewmodels.GroupsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repositories
    single { GroupRepository() }
    
    // ViewModels
    viewModel { GroupsViewModel(get()) }
} 