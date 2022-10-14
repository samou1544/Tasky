package com.asma.tasky.authentication.data.remote.response

data class AuthenticationResponse(
    val token:String,
    val userId:String,
    val fullName:String
)
