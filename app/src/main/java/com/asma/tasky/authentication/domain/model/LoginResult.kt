package com.asma.tasky.authentication.domain.model

import com.asma.tasky.authentication.domain.util.AuthError
import com.asma.tasky.common.Resource

data class LoginResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: Resource<Unit>? = null
)
