package com.asma.tasky.authentication.data.repository

import com.asma.tasky.authentication.data.remote.AuthenticationApi
import com.asma.tasky.authentication.data.remote.request.LoginRequest
import com.asma.tasky.authentication.data.remote.request.RegistrationRequest
import com.asma.tasky.authentication.domain.repository.AuthenticationRepository
import com.asma.tasky.common.Resource
import retrofit2.HttpException
import java.io.IOException

class AuthenticationRepositoryImpl(
    private val api: AuthenticationApi
) : AuthenticationRepository {

    override suspend fun register(name: String, email: String, password: String): Resource<Unit> {
        val registrationRequest =
            RegistrationRequest(fullName = name, email = email, password = password)
        return try {
            val result = api.register(request = registrationRequest)
            if (result.isSuccessful) Resource.Success(Unit)
            else Resource.Error(result.message() ?: "Unknown Error")
        } catch (e: IOException) {
            Resource.Error("Network error. Check your internet connection")
        } catch (e: HttpException) {
            Resource.Error("Something went wrong. Try again later")
        }
    }

    override suspend fun login(email: String, password: String): Resource<Unit> {
        val loginRequest = LoginRequest(email, password)
        return try {
            val result = api.login(loginRequest)
            if (result.isSuccessful) {
                //todo save token locally
                Resource.Success(Unit)
            } else Resource.Error(result.message() ?: "Unknown Error")
        } catch (e: IOException) {
            Resource.Error("Network error. Check your internet connection")
        } catch (e: HttpException) {
            Resource.Error("Something went wrong. Try again later")
        }
    }

    override suspend fun authenticate(): Resource<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Resource<Unit> {
        TODO("Not yet implemented")
    }

}