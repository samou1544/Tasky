package com.asma.tasky.feature_authentication.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asma.tasky.R
import com.asma.tasky.feature_authentication.domain.util.AuthError
import com.asma.tasky.feature_authentication.domain.util.Validation
import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.domain.states.TextFieldState
import com.asma.tasky.core.presentation.util.UiEvent
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_authentication.domain.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _nameState = mutableStateOf(TextFieldState())
    val nameState: State<TextFieldState> = _nameState

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _registerState = mutableStateOf(RegisterState())
    val registerState: State<RegisterState> = _registerState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _onRegister = MutableSharedFlow<Unit>(replay = 1)
    val onRegister = _onRegister.asSharedFlow()

    fun onEvent(event: RegisterEvent) {

        when (event) {
            is RegisterEvent.NameEntered -> {
                val name = event.name
                _nameState.value = _nameState.value.copy(
                    text = name
                )
                validateName(name)
            }
            is RegisterEvent.EmailEntered -> {
                val email = event.email
                _emailState.value = _emailState.value.copy(
                    text = email
                )
                validateEmail(email)
            }
            is RegisterEvent.PasswordEntered -> {
                val password = event.password
                _passwordState.value = _passwordState.value.copy(
                    text = password
                )
                validatePassword(password)

            }
            is RegisterEvent.TogglePasswordVisibility -> {
                _registerState.value = _registerState.value.copy(
                    isPasswordVisible = !_registerState.value.isPasswordVisible
                )
            }
            is RegisterEvent.Register -> {
                val name = _nameState.value.text

                val email = _emailState.value.text

                val password = _passwordState.value.text

                if (validateName(name) != null || validateEmail(email) != null || validatePassword(
                        password
                    ) != null
                ) {
                    return
                }
                viewModelScope.launch {
                    _registerState.value = _registerState.value.copy(isLoading = true)
                    val registerResult = repository.register(
                        name = nameState.value.text,
                        email = emailState.value.text,
                        password = passwordState.value.text
                    )
                    _registerState.value = _registerState.value.copy(isLoading = false)
                    when (registerResult) {
                        is Resource.Success -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(UiText.StringResource(R.string.successfully_registered))
                            )
                            _onRegister.emit(Unit)
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    registerResult.uiText ?: UiText.unknownError()
                                )
                            )
                            _registerState.value = _registerState.value.copy(isLoading = false)

                        }
                    }

                }
            }
        }
    }

    private fun validateName(name: String): AuthError? {
        val nameError = Validation.validateName(name)
        _nameState.value = _nameState.value.copy(
            error = nameError
        )
        _registerState.value = _registerState.value.copy(isNameValid = nameError == null)
        return nameError
    }

    private fun validateEmail(email: String): AuthError? {
        val emailError = Validation.validateEmail(email)
        _emailState.value = _emailState.value.copy(
            error = emailError
        )
        _registerState.value = _registerState.value.copy(isEmailValid = emailError == null)
        return emailError
    }

    private fun validatePassword(password: String): AuthError? {
        val passwordError = Validation.validatePassword(password)
        _passwordState.value = _passwordState.value.copy(
            error = passwordError
        )
        _registerState.value =
            _registerState.value.copy(isPasswordValid = passwordError == null)
        return passwordError
    }

}