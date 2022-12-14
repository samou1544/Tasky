package com.asma.tasky.feature_management.presentation.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asma.tasky.feature_management.domain.agenda.use_case.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    init {
        //todo get username
        getTasks(LocalDate.now())
    }

    private val _agendaState = MutableStateFlow(AgendaState())
    val agendaState = _agendaState.asStateFlow()

    private fun getTasks(day: LocalDate) {
        getTasksUseCase(day).onEach { result ->
            _agendaState.update {
                it.copy(items = result)
            }
        }.launchIn(viewModelScope)
    }
}
