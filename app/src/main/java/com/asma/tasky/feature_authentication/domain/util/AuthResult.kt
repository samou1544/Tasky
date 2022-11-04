package com.asma.tasky.feature_authentication.domain.util

sealed class AuthResult : com.asma.tasky.core.util.Error() {
    object Success : AuthResult()
    object Unauthorized : AuthResult()
    object Error : AuthResult()
}
