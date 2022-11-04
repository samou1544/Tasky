package com.asma.tasky.feature_authentication.presentation.login

sealed class LoginEvent {
    data class EmailEntered(val email: String): LoginEvent()
    data class PasswordEntered(val password: String): LoginEvent()
    object Login: LoginEvent()
    object TogglePasswordVisibility: LoginEvent()
}
