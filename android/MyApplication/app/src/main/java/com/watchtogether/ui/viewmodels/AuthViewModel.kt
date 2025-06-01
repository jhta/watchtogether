package com.watchtogether.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.watchtogether.data.repositories.AuthRepository
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Data class representing the authentication state
 */
data class AuthUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val user: UserInfo? = null,
    val error: String? = null,
    val isInitialCheckComplete: Boolean = false
)

/**
 * ViewModel to handle authentication related operations and state
 */
class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()
    
    init {
        checkCurrentAuthState()
    }
    
    /**
     * Check current authentication state and update UI state
     * Modern Supabase-kt automatically handles session loading from storage
     */
    private fun checkCurrentAuthState() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                
                // Check auth state - Supabase-kt automatically loads from storage
                val isLoggedIn = authRepository.isLoggedIn()
                val currentUser = authRepository.getCurrentUser()
                
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        isLoggedIn = isLoggedIn,
                        user = currentUser,
                        isInitialCheckComplete = true,
                        error = null
                    )
                }
                
                Log.d("AuthViewModel", "Auth state checked. Logged in: $isLoggedIn, User: ${currentUser?.id}")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error checking auth state", e)
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        isInitialCheckComplete = true,
                        error = "Failed to check authentication state"
                    )
                }
            }
        }
    }
    
    /**
     * Refresh the current authentication state
     * Useful when returning from OAuth flow
     */
    fun refreshAuthState() {
        checkCurrentAuthState()
    }
    
    /**
     * Sign in with Google
     */
    fun signInWithGoogle() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            authRepository.signInWithGoogle()
                .onSuccess {
                    // The OAuth flow will redirect to the browser and then back to the app
                    // The actual update of the logged-in state will happen when the app restarts
                    // or through the deep link handler activity
                    _uiState.update { 
                        it.copy(isLoading = false)
                    }
                }
                .onFailure { exception ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = "Failed to sign in with Google: ${exception.message}"
                        )
                    }
                }
        }
    }
    
    /**
     * Sign out the current user
     */
    fun signOut() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            authRepository.signOut()
                .onSuccess {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            user = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = "Failed to sign out: ${exception.message}"
                        )
                    }
                }
        }
    }
} 