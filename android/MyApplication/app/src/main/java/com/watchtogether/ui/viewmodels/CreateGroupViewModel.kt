package com.watchtogether.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.watchtogether.data.repositories.GroupRepository
import com.watchtogether.models.Group
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class CreateGroupUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val createdGroup: Group? = null,
    val name: String = "",
    val description: String = "",
    val invitationCode: String = generateInvitationCode()
)

private fun generateInvitationCode(): String {
    return UUID.randomUUID().toString().substring(0, 8).uppercase()
}

class CreateGroupViewModel(
    private val groupRepository: GroupRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CreateGroupUiState())
    val uiState: StateFlow<CreateGroupUiState> = _uiState.asStateFlow()
    
    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }
    
    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }
    
    fun createGroup() {
        val name = _uiState.value.name
        val description = _uiState.value.description.ifBlank { null }
        
        Log.d("CreateGroupViewModel", "Creating group with name: $name, description: $description")
        if (name.isBlank()) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, success = false) }
            
            groupRepository.createGroup(name, description)
                .onSuccess { group ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            success = true,
                            createdGroup = group
                        )
                    }
                    Log.d("CreateGroupViewModel", "Group created successfully: $group")
                }
                .onFailure { exception ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = "Failed to create group: ${exception.message}"
                        )
                    }
                    Log.e("CreateGroupViewModel", "Error creating group", exception)
                }
        }
    }
    
    fun resetState() {
        _uiState.update { 
            CreateGroupUiState(invitationCode = it.invitationCode)
        }
    }
} 