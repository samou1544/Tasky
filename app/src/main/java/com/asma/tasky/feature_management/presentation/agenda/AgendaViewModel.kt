package com.asma.tasky.feature_management.presentation.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asma.tasky.core.presentation.util.UiEvent
import com.asma.tasky.feature_management.domain.agenda.use_case.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    private val _agendaState = MutableStateFlow(AgendaState())
    val agendaState = _agendaState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getTasks() {
        getTasksUseCase().onEach { result ->
            _agendaState.update {
                it.copy(items = result)
            }
        }.launchIn(viewModelScope)
    }
}
