package com.asma.tasky.core.domain.states

data class TextFieldState(
    val text: String = "",
    val error: com.asma.tasky.core.util.Error? = null
)
