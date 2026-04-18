package com.andresdevs.restaurant.presentation.main

import com.andresdevs.restaurant.domain.model.UserRole

data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val message: String? = null,
    val role: UserRole? = null,
    val isAuthenticated: Boolean = false
)
