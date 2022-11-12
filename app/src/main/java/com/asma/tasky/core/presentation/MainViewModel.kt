package com.asma.tasky.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asma.tasky.feature_authentication.domain.repository.AuthenticationRepository
import com.asma.tasky.feature_authentication.domain.util.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _showMenu = MutableStateFlow(false)
    val showMenu = _showMenu.asStateFlow()

    private val _isAuthenticating = MutableStateFlow(true)
    val isAuthenticating = _isAuthenticating.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            _isAuthenticating.value = true
            when (repository.authenticate()) {
                is AuthResult.Success -> _isLoggedIn.value = true
                is AuthResult.Error -> _isLoggedIn.value = true
                is AuthResult.Unauthorized -> _isLoggedIn.value = false
            }
            _isAuthenticating.value = false
        }
    }

    fun setShowMenu(value: Boolean) {
        _showMenu.update {
            value
        }
    }
}
