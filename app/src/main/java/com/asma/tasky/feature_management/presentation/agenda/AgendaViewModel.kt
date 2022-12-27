package com.asma.tasky.feature_management.presentation.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.agenda.use_case.GetAgendaItemsUseCase
import com.asma.tasky.feature_management.domain.task.use_case.AddTaskUseCase
import com.asma.tasky.feature_management.domain.task.use_case.DeleteTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val getAgendaItemsUseCase: GetAgendaItemsUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase:DeleteTaskUseCase,
) : ViewModel() {

    init {
        // todo get username
        getAgendaItems(LocalDate.now())
    }

    private val _agendaState = MutableStateFlow(AgendaState())
    val agendaState = _agendaState.asStateFlow()

    private fun getAgendaItems(day: LocalDate) {
        getAgendaItemsUseCase(day).onEach { result ->
            _agendaState.update {
                it.copy(items = result)
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: AgendaEvent) {
        when (event) {
            is AgendaEvent.DateSelected -> {
                _agendaState.update {
                    it.copy(selectedDate = event.date.atStartOfDay(), selectedDay = event.date)
                }
                getAgendaItems(event.date)
            }
            is AgendaEvent.DaySelected -> {
                _agendaState.update {
                    it.copy(selectedDay = event.day)
                }
                getAgendaItems(event.day)
            }
            is AgendaEvent.ToggleTaskIsDone -> toggleTaskDone(task = event.task)
            is AgendaEvent.DeleteItem -> {
                when(event.item){
                    is AgendaItem.Task->{
                        deleteTask(event.item)
                    }
                    is AgendaItem.Event -> TODO()
                    is AgendaItem.Reminder -> TODO()
                }
            }
        }
    }

    private fun toggleTaskDone(task:AgendaItem.Task){
        viewModelScope.launch {
            addTaskUseCase(task.copy(isDone = task.isDone.not()), isNewTask = false)
        }

    }

    private fun deleteTask(task: AgendaItem.Task){
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }
}
