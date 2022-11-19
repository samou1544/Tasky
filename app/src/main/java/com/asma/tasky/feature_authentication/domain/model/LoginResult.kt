package com.asma.tasky.feature_authentication.domain.model

import com.asma.tasky.core.util.Resource
import com.asma.tasky.feature_authentication.domain.util.AuthError

data class LoginResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: Resource<Unit>? = null
)
