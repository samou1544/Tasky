package com.asma.tasky.authentication.domain.repository

import com.asma.tasky.common.Resource

interface AuthenticationRepository {

    suspend fun register(name: String, email: String, password: String): Resource<Unit>

    suspend fun login(email: String, password: String): Resource<Unit>

    suspend fun authenticate(): Resource<Unit>

    suspend fun logout(): Resource<Unit>

}