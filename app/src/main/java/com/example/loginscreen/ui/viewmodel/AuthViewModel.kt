package com.example.loginscreen.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginscreen.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _authStatus = MutableStateFlow<AuthStatus>(AuthStatus.Idle)
    val authStatus: StateFlow<AuthStatus> = _authStatus

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            if (result.isSuccess) {
                fetchRoleAndEmitSuccess()
            } else {
                _isLoading.value = false
                _authStatus.value = AuthStatus.Error(result.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }

    fun signup(email: String, password: String, name: String, phone: String, role: String, imageUri: android.net.Uri? = null) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = authRepository.signup(email, password, name, phone, role, imageUri)
            if (result.isSuccess) {
                _isLoading.value = false
                _authStatus.value = AuthStatus.Success(role)
            } else {
                _isLoading.value = false
                _authStatus.value = AuthStatus.Error(result.exceptionOrNull()?.message ?: "Signup failed")
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _authStatus.value = AuthStatus.Idle
    }

    fun checkUserStatus() {
        if (authRepository.isUserLoggedIn()) {
            _isLoading.value = true
            viewModelScope.launch {
                fetchRoleAndEmitSuccess()
            }
        }
    }

    private suspend fun fetchRoleAndEmitSuccess() {
        try {
            val uid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                val snapshot = com.google.firebase.firestore.FirebaseFirestore.getInstance()
                    .collection("users").document(uid).get().await()
                val role = snapshot.getString("role") ?: "patient"
                _isLoading.value = false
                _authStatus.value = AuthStatus.Success(role)
            } else {
                _isLoading.value = false
                _authStatus.value = AuthStatus.Success("patient")
            }
        } catch (e: Exception) {
            _isLoading.value = false
            _authStatus.value = AuthStatus.Success("patient")
        }
    }
}

sealed class AuthStatus {
    object Idle : AuthStatus()
    data class Success(val role: String) : AuthStatus()
    data class Error(val message: String) : AuthStatus()
}
