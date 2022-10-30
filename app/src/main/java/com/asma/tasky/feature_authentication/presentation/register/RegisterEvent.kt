package com.asma.tasky.feature_authentication.presentation.register

sealed class RegisterEvent {
    data class NameEntered(val name: String): RegisterEvent()
    data class EmailEntered(val email: String): RegisterEvent()
    data class PasswordEntered(val password: String): RegisterEvent()
    object Register: RegisterEvent()
    object TogglePasswordVisibility: RegisterEvent()
}
