package com.asma.tasky.authentication.data.remote.request

data class RegistrationRequest(
    val fullName:String,
    val email:String,
    val password:String
)
