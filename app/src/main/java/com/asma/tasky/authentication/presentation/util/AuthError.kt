package com.asma.tasky.authentication.presentation.util

sealed class AuthError : com.asma.tasky.core.util.Error() {
    object FieldEmpty : AuthError()
    object InputTooShort : AuthError()
    object InvalidEmail: AuthError()
    object InvalidPassword : AuthError()
}
