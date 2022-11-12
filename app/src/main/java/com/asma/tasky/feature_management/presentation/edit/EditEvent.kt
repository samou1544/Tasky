package com.asma.tasky.feature_management.presentation.edit

sealed class EditEvent {
    data class TextEntered(val text: String) : EditEvent()
}
