package com.asma.tasky.authentication.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asma.tasky.authentication.domain.use_case.LoginUseCase
import com.asma.tasky.common.Resource
import com.asma.tasky.core.domain.states.TextFieldState
import com.asma.tasky.core.presentation.util.UiEvent
import com.asma.tasky.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
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

    fun onEvent(event: RegisterEvent) {

        when (event) {
            is RegisterEvent.NameEntered -> {
                _emailState.value = emailState.value.copy(
                    text = event.name
                )
            }
            is RegisterEvent.EmailEntered -> {
                _emailState.value = emailState.value.copy(
                    text = event.email
                )
            }
            is RegisterEvent.PasswordEntered -> {
                _passwordState.value = passwordState.value.copy(
                    text = event.password
                )
            }
            is RegisterEvent.TogglePasswordVisibility -> {
                _registerState.value = _registerState.value.copy(
                    isPasswordVisible = !_registerState.value.isPasswordVisible
                )
            }
            is RegisterEvent.Register -> {
                viewModelScope.launch {
                    _registerState.value = _registerState.value.copy(isLoading = true)
                    val loginResult = loginUseCase(
                        email = emailState.value.text,
                        password = passwordState.value.text
                    )
                    _registerState.value = _registerState.value.copy(isLoading = false)
                    if (loginResult.emailError != null) {
                        _emailState.value = emailState.value.copy(
                            error = loginResult.emailError
                        )
                    }
                    if (loginResult.passwordError != null) {
                        _passwordState.value = _passwordState.value.copy(
                            error = loginResult.passwordError
                        )
                    }
                    when (loginResult.result) {
                        is Resource.Success -> {
                            _eventFlow.emit(UiEvent.OnLogin)
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    loginResult.result.uiText ?: UiText.unknownError()
                                )
                            )
                        }
                        else -> {}
                    }

                }
            }
        }
    }
}