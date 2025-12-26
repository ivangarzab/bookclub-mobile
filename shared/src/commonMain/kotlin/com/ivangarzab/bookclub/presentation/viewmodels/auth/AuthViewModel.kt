package com.ivangarzab.bookclub.presentation.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivangarzab.bookclub.data.auth.AuthRepository
import com.ivangarzab.bookclub.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The purpose of this [ViewModel] class is to handle authentication logic for the
 * presentation layer.
 */
class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.initialize()
            authRepository.currentUser.collect { user: User? ->
                _state.update {
                    user?.let {
                        AuthState.Authenticated(it)
                    } ?: AuthState.Unauthenticated
                }
            }
        }
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        _state.update{ AuthState.Loading }
        authRepository.signUpWithEmail(email, password)
            .onSuccess { user ->
                _state.update { AuthState.Authenticated(user) }
            }
            .onFailure { error ->
                _state.update { AuthState.Error(error.message ?: "Error signing up") }
            }
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        _state.update{ AuthState.Loading }
        authRepository.signInWithEmail(email, password)
            .onSuccess { user ->
                _state.update { AuthState.Authenticated(user) }
            }
            .onFailure { error ->
                _state.update { AuthState.Error(error.message ?: "Error signing in") }
            }
    }

    fun signOut() = viewModelScope.launch {
        _state.update{ AuthState.Loading }
        authRepository.signOut()
            .onSuccess { _state.update { AuthState.Unauthenticated } }
            .onFailure { error ->
                _state.update { AuthState.Error(error.message ?: "Error signing out") }
            }
    }
}

sealed class AuthState {
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data class Authenticated(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}