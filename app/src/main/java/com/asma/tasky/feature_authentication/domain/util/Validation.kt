package com.asma.tasky.feature_authentication.domain.util

import android.util.Patterns
import com.asma.tasky.core.util.Constants

object Validation {

    fun validateName(name: String): AuthError? {
        val trimmedUsername = name.trim()
        if (trimmedUsername.isBlank()) {
            return AuthError.FieldEmpty
        }
        if (trimmedUsername.length < Constants.MIN_NAME_LENGTH) {
            return AuthError.InputTooShort
        }
        if (trimmedUsername.length > Constants.MAX_NAME_LENGTH) {
            return AuthError.InputTooLong
        }
        return null
    }

    fun validateEmail(email: String): AuthError? {
        val trimmedEmail = email.trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return AuthError.InvalidEmail
        }
        if (trimmedEmail.isBlank()) {
            return AuthError.FieldEmpty
        }
        return null
    }

    fun validatePassword(password: String): AuthError? {
        val passwordContainsUppercaseLetters = password.any { it.isUpperCase() }
        val passwordContainsLowercaseLetters = password.any { it.isLowerCase() }
        val passwordContainsDigits = password.any { it.isDigit() }
        if (!passwordContainsUppercaseLetters || !passwordContainsLowercaseLetters || !passwordContainsDigits) {
            return AuthError.InvalidPassword
        }
        if (password.length < Constants.MIN_PASSWORD_LENGTH) {
            return AuthError.InputTooShort
        }
        if (password.isBlank()) {
            return AuthError.FieldEmpty
        }
        return null
    }
}
