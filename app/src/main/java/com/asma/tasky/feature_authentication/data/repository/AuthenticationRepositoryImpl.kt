package com.asma.tasky.feature_authentication.data.repository

import android.content.SharedPreferences
import com.asma.tasky.R
import com.asma.tasky.core.util.Constants
import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_authentication.data.remote.AuthenticationApi
import com.asma.tasky.feature_authentication.data.remote.request.LoginRequest
import com.asma.tasky.feature_authentication.data.remote.request.RegistrationRequest
import com.asma.tasky.feature_authentication.domain.repository.AuthenticationRepository
import com.asma.tasky.feature_authentication.domain.util.AuthResult
import retrofit2.HttpException
import java.io.IOException

class AuthenticationRepositoryImpl(
    private val api: AuthenticationApi,
    private val sharedPreferences: SharedPreferences
) : AuthenticationRepository {

    override suspend fun register(name: String, email: String, password: String): Resource<Unit> {
        val registrationRequest =
            RegistrationRequest(fullName = name, email = email, password = password)
        return try {
            val result = api.register(request = registrationRequest)
            if (result.isSuccessful) Resource.Success(Unit)
            else Resource.Error(UiText.DynamicString(result.message()))

        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(R.string.netword_error))
        } catch (e: HttpException) {
            Resource.Error(UiText.StringResource(R.string.something_wrong))
        }
    }

    override suspend fun login(email: String, password: String): Resource<Unit> {
        val loginRequest = LoginRequest(email, password)
        return try {
            val result = api.login(loginRequest)
            if (result.isSuccessful) {
                result.body()?.let { response ->
                    response.fullName
                    println("JWT = ${response.token}")
                    sharedPreferences.edit()
                        .putString(Constants.KEY_JWT_TOKEN, response.token)
                        .putString(Constants.KEY_USER_ID, response.userId)
                        .apply()
                }
                Resource.Success(Unit)
            } else
                if (result.errorBody() != null)
                    result.errorBody()!!.let {
                        Resource.Error(UiText.DynamicString(it.string()))
                    }
                else Resource.Error(UiText.StringResource(R.string.unknown_error))

        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(R.string.netword_error))
        } catch (e: HttpException) {
            Resource.Error(UiText.StringResource(R.string.something_wrong))
        }
    }

    override suspend fun authenticate(): AuthResult {
        return try {
            val result = api.authenticate()
            when (result.code()) {
                200 -> AuthResult.Success
                401 -> AuthResult.Unauthorized
                else -> AuthResult.Error
            }
        } catch (e: Exception) {
            AuthResult.Error
        }
    }

    override suspend fun logout(): Resource<Unit> {
        return try {
            api.logout()
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(UiText.StringResource(R.string.netword_error))
        } catch (e: HttpException) {
            Resource.Error(UiText.StringResource(R.string.something_wrong))
        }
    }

}