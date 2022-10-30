package com.asma.tasky.feature_authentication.data.remote

import com.asma.tasky.feature_authentication.data.remote.request.LoginRequest
import com.asma.tasky.feature_authentication.data.remote.request.RegistrationRequest
import com.asma.tasky.feature_authentication.data.remote.response.AuthenticationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationApi {

    @POST("/register")
    suspend fun register(
        @Body request: RegistrationRequest
    ): Response<Unit>

    @POST("/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthenticationResponse>

    @GET("/authenticate")
    suspend fun authenticate(): Response<Unit>

    @GET("/logout")
    suspend fun logout()

    companion object {
        const val BASE_URL = "https://tasky.pl-coding.com/"
    }
}