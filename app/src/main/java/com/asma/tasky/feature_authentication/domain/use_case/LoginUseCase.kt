package com.asma.tasky.feature_authentication.domain.use_case

import com.asma.tasky.feature_authentication.domain.model.LoginResult
import com.asma.tasky.feature_authentication.domain.repository.AuthenticationRepository
import com.asma.tasky.feature_authentication.domain.util.AuthError
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(email: String, password: String): LoginResult {

        val emailError = if (email.isBlank()) AuthError.FieldEmpty else null
        val passwordError = if (password.isBlank()) AuthError.FieldEmpty else null

        if (emailError != null || passwordError != null) {
            return LoginResult(emailError, passwordError)
        }

        return LoginResult(
            result = repository.login(email, password)
        )
    }
}
