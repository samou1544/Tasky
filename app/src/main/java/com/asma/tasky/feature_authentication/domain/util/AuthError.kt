package com.asma.tasky.feature_authentication.domain.util

sealed class AuthError : com.asma.tasky.core.util.Error() {
    object FieldEmpty : AuthError()
    object InputTooShort : AuthError()
    object InputTooLong : AuthError()
    object InvalidEmail : AuthError()
    object InvalidPassword : AuthError()
}
