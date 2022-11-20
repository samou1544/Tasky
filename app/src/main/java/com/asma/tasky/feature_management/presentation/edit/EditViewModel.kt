package com.asma.tasky.feature_management.presentation.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.asma.tasky.core.domain.states.TextFieldState
import com.asma.tasky.core.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _text = MutableStateFlow(TextFieldState())
    val text = _text.asStateFlow()

    private val _key = MutableStateFlow("")
    val key = _key.asStateFlow()

    init {
        savedStateHandle.get<String>(Constants.PARAM_TITLE)?.let { title ->
            _key.update {
                title
            }
        }
        savedStateHandle.get<String>(Constants.PARAM_TEXT)?.let { text ->
            _text.update {
                it.copy(text = text)
            }
        }
    }

    fun onEvent(event: EditEvent) {
        when (event) {
            is EditEvent.TextEntered -> {
                _text.update {
                    it.copy(text = event.text)
                }
            }
        }
    }
}
