package com.watchtogether.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.watchtogether.data.repositories.GroupRepository
import com.watchtogether.models.Group
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GroupsUiState(
    val groups: List<Group> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class GroupsViewModel(
    private val groupRepository: GroupRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(GroupsUiState(isLoading = true))
    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()
    
    init {
        loadGroups()
    }
    
    private fun loadGroups() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            groupRepository.getGroups()
                .onSuccess { groups ->
                    _uiState.update { 
                        it.copy(
                            groups = groups,
                            isLoading = false
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = "Failed to load groups: ${exception.message}"
                        )
                    }
                }
        }
    }
    
    fun refreshGroups() {
        loadGroups()
    }
    
    fun createGroup(name: String, onSuccess: (Group) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            groupRepository.createGroup(name)
                .onSuccess { newGroup ->
                    // Add the new group to the current list
                    val updatedGroups = _uiState.value.groups + newGroup
                    _uiState.update { it.copy(groups = updatedGroups, isLoading = false) }
                    onSuccess(newGroup)
                }
                .onFailure { exception ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = "Failed to create group: ${exception.message}"
                        )
                    }
                }
        }
    }
} 