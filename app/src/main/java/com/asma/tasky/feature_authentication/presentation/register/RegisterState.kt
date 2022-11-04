package com.asma.tasky.feature_authentication.presentation.register

data class RegisterState(
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isPasswordValid: Boolean? = null,
    val isEmailValid: Boolean? = null,
    val isNameValid: Boolean? = null
)
