package com.asma.tasky.feature_authentication.data.remote.response

data class AuthenticationResponse(
    val token:String,
    val userId:String,
    val fullName:String
)
