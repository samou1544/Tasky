package com.asma.tasky.feature_authentication.domain.model

import com.asma.tasky.feature_authentication.domain.util.AuthError
import com.asma.tasky.core.util.Resource

data class LoginResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: Resource<Unit>? = null
)
