package com.asma.tasky.core.presentation.util

import com.asma.tasky.core.util.Event
import com.asma.tasky.core.util.UiText

sealed class UiEvent : Event() {
    data class ShowSnackbar(val uiText: UiText) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object OnLogin : UiEvent()
}