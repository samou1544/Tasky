package com.asma.tasky.feature_authentication.data.remote.request

data class RegistrationRequest(
    val fullName: String,
    val email: String,
    val password: String
)
