package com.asma.tasky.feature_authentication.domain.repository

import com.asma.tasky.core.util.Resource
import com.asma.tasky.feature_authentication.domain.util.AuthResult

interface AuthenticationRepository {

    suspend fun register(name: String, email: String, password: String): Resource<Unit>

    suspend fun login(email: String, password: String): Resource<Unit>

    suspend fun authenticate(): AuthResult

    suspend fun logout(): Resource<Unit>
}
