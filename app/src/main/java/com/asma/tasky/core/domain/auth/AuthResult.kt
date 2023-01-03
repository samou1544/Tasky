package com.asma.tasky.core.domain.auth

import com.asma.tasky.R
import com.asma.tasky.core.util.UiText

sealed class AuthResult<T>(
    val isAuthorized: Boolean,
    val data: T? = null,
    val message: UiText? = null
) {
    class Success<T>(data: T, message: UiText? = null): AuthResult<T>(
        isAuthorized = true,
        data = data,
        message = message
    )

    class Unauthorized<T>: AuthResult<T>(
        isAuthorized = false,
        message = UiText.StringResource(R.string.error_unauthorized)
    )

    class Error<T>(
        message: UiText,
        data: T? = null
    ): AuthResult<T>(
        isAuthorized = true,
        message = message,
        data = data
    )
}
