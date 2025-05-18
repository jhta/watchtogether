package com.watchtogether.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.watchtogether.data.repositories.PollRepository
import com.watchtogether.models.Poll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GroupDetailUiState(
    val polls: List<Poll> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class GroupDetailViewModel(
    private val pollRepository: PollRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(GroupDetailUiState(isLoading = false))
    val uiState: StateFlow<GroupDetailUiState> = _uiState.asStateFlow()
    
    fun loadPolls(groupId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            pollRepository.getPollsByGroupId(groupId)
                .onSuccess { polls ->
                    _uiState.update { 
                        it.copy(
                            polls = polls,
                            isLoading = false
                        )
                    }
                }
                .onFailure { exception ->
                    Log.e("GroupDetailViewModel", "Error loading polls", exception)
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = "Failed to load polls: ${exception.message}"
                        )
                    }
                }
        }
    }
} 