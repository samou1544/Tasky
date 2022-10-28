package com.asma.tasky.authentication.domain.use_case

import com.asma.tasky.authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    suspend operator fun invoke(name:String, email: String, password: String){

    }
}