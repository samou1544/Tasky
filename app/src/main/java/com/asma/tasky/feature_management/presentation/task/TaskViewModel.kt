package com.asma.tasky.feature_management.presentation.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asma.tasky.feature_management.domain.reminder.ReminderManager
import com.asma.tasky.core.presentation.util.UiEvent
import com.asma.tasky.core.util.Constants
import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.use_case.AddTaskUseCase
import com.asma.tasky.feature_management.domain.task.use_case.DeleteTaskUseCase
import com.asma.tasky.feature_management.domain.task.use_case.GetTaskUseCase
import com.asma.tasky.feature_management.domain.util.DateUtil
import com.asma.tasky.feature_management.domain.util.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val reminderManager: ReminderManager,
    getTaskUseCase: GetTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskState = MutableStateFlow(TaskState())
    val taskState = _taskState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Boolean>(Constants.PARAM_EDITABLE)?.let { editable ->
            _taskState.update {
                it.copy(isEditable = editable)
            }
        }

        val taskId = savedStateHandle.get<String>(Constants.PARAM_ID)

        if (!taskId.isNullOrEmpty()) {
            viewModelScope.launch {
                when (val result = getTaskUseCase(taskId)) {
                    is Resource.Success -> {
                        result.data?.let { task ->
                            updateState(task)
                        }
                    }
                    is Resource.Error -> {}
                }
            }
        } else {
            // this is a new task, turn on editable mode
            _taskState.update {
                it.copy(isEditable = true)
            }
        }
    }

    private fun updateState(task: AgendaItem.Task) {
        _taskState.update {
            it.copy(task = task)
        }
        _taskState.update {
            it.copy(showDeleteTask = true)
        }
        task.startDate.let { time ->
            _taskState.update {
                it.copy(taskTime = DateUtil.secondsToLocalDateTime(time))
            }
        }
        _taskState.update {
            it.copy(
                taskReminder = computeReminder(
                    startTime = task.startDate,
                    reminderTime = task.reminder
                )
            )
        }
        // todo check if the task is already done
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.TitleEntered -> {
                _taskState.update {
                    it.copy(task = it.task.copy(taskTitle = event.title))
                }
            }
            is TaskEvent.DescriptionEntered -> {
                _taskState.update {
                    it.copy(task = it.task.copy(taskDescription = event.description))
                }
            }
            is TaskEvent.TimeSelected -> {
                _taskState.update {
                    it.copy(taskTime = it.taskTime.with(event.time))
                }
            }
            is TaskEvent.DateSelected -> {
                _taskState.update {
                    it.copy(taskTime = it.taskTime.with(event.date))
                }
            }
            is TaskEvent.ReminderSelected -> {
                _taskState.update {
                    it.copy(taskReminder = event.reminder)
                }
            }
            is TaskEvent.Save -> {
                _taskState.update {
                    it.copy(isLoading = true)
                }

                val task = _taskState.value.task.copy(
                    taskStartDate = DateUtil.localDateTimeToSeconds(_taskState.value.taskTime),
                    isDone = false,
                    taskReminder = computeReminderSeconds(
                        _taskState.value.taskReminder,
                        _taskState.value.taskTime
                    ),
                    taskId = _taskState.value.task.id
                )

                viewModelScope.launch {
                    when (val result = addTaskUseCase(task)) {
                        is Resource.Success -> {
                            reminderManager.startReminderWorker(agendaItem = task)
                            // todo push data to server
                            _eventFlow.emit(UiEvent.NavigateUp)
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
                }
            }
            is TaskEvent.Delete -> {
                viewModelScope.launch {
                    when (val result = deleteTaskUseCase(_taskState.value.task)) {
                        is Resource.Success -> {
                            // todo remove task from server
                            reminderManager.cancelReminderWorker(agendaItem = _taskState.value.task)
                            _taskState.update {
                                it.copy(isLoading = false)
                            }
                            _eventFlow.emit(UiEvent.NavigateUp)

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
                }
            }
            is TaskEvent.ToggleReminderDropDown -> {
                _taskState.update {
                    it.copy(showReminderDropDown = it.showReminderDropDown.not())
                }
            }
            is TaskEvent.ToggleEditMode -> {
                _taskState.update {
                    it.copy(isEditable = it.isEditable.not())
                }
            }
        }
    }

    private fun computeReminderSeconds(reminder: Reminder, startTime: LocalDateTime): Long {
        return DateUtil.localDateTimeToSeconds(startTime) - reminder.seconds
    }

    private fun computeReminder(reminderTime: Long, startTime: Long): Reminder {
        return when (startTime - reminderTime) {
            Reminder.OneHourBefore.seconds -> {
                Reminder.OneHourBefore
            }
            Reminder.OneDayBefore.seconds -> {
                Reminder.OneDayBefore
            }
            Reminder.ThirtyMinutesBefore.seconds -> {
                Reminder.ThirtyMinutesBefore
            }
            Reminder.TenMinutesBefore.seconds -> {
                Reminder.TenMinutesBefore
            }
            Reminder.SixHoursBefore.seconds -> {
                Reminder.SixHoursBefore
            }
            else -> {
                Reminder.OneHourBefore
            }
        }
    }


}
