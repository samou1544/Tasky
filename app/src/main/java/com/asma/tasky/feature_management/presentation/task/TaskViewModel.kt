package com.asma.tasky.feature_management.presentation.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asma.tasky.core.presentation.util.UiEvent
import com.asma.tasky.core.util.Constants
import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.use_case.AddTaskUseCase
import com.asma.tasky.feature_management.domain.util.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskState = MutableStateFlow(TaskState())
    val taskState = _taskState.asStateFlow()

    private val _taskTitle = MutableStateFlow("")
    val taskTitle = _taskTitle.asStateFlow()

    private val _taskDescription = MutableStateFlow("")
    val taskDescription = _taskDescription.asStateFlow()

    private val _editModeState = MutableStateFlow(false)
    val editModeState = _editModeState.asStateFlow()

    private val _taskTime = MutableStateFlow(LocalDateTime.now())
    val taskTime = _taskTime.asStateFlow()

    private val _taskReminder = MutableStateFlow<Reminder>(Reminder.OneHourBefore)
    val taskReminder = _taskReminder.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Boolean>(Constants.PARAM_EDITABLE)?.let {
            _editModeState.update { it }
        }

        val taskId = savedStateHandle.get<String>(Constants.PARAM_ID)

        if (!taskId.isNullOrEmpty()) {
            //todo load task from database
            _taskState.update {
                it.copy(showDeleteTask = true)
            }
        } else {
            //this is a new task, turn on editable mode
            _editModeState.update { true }

            _taskTime.update {
                LocalDateTime.ofEpochSecond(System.currentTimeMillis() / 1000, 0, ZoneOffset.UTC)
            }

        }
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.TitleEntered -> {
                _taskTitle.update { event.title ?: "" }
            }
            is TaskEvent.DescriptionEntered -> {
                _taskDescription.update { event.description ?: "" }
            }
            is TaskEvent.TimeSelected -> {
                _taskTime.update {
                    it.with(event.time)
                }
            }
            is TaskEvent.DateSelected -> {
                _taskTime.update {
                    it.with(event.date)
                }
            }
            is TaskEvent.ReminderSelected -> {
                _taskReminder.update {
                    event.reminder
                }
            }
            is TaskEvent.Save -> {
                _taskState.update {
                    it.copy(isLoading = true)
                }
                val task = AgendaItem.Task(
                    title = _taskTitle.value,
                    description = taskDescription.value,
                    startDate = _taskTime.value.toEpochSecond(ZoneOffset.UTC)
                )
                addTaskUseCase(task).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            //todo push data to server
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    result.uiText ?: UiText.unknownError()
                                )
                            )
                            _taskState.update {
                                it.copy(isLoading = false)
                            }
                        }
                    }
                }.launchIn(viewModelScope)

            }
            is TaskEvent.Delete -> {}
            is TaskEvent.ToggleReminderDropDown -> {
                _taskState.update {
                    it.copy(showReminderDropDown = it.showReminderDropDown.not())
                }
            }
        }
    }

}