package com.asma.tasky.feature_management.presentation.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.asma.tasky.core.util.Constants
import com.asma.tasky.feature_management.domain.util.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskState = MutableStateFlow(TaskState())
    val taskState = _taskState.asStateFlow()

    private val _editModeState = MutableStateFlow(false)
    val editModeState = _editModeState.asStateFlow()

    private val _taskTime = MutableStateFlow(LocalDateTime.now())
    val taskTime = _taskTime.asStateFlow()

    private val _taskReminder = MutableStateFlow<Reminder>(Reminder.OneHourBefore)
    val taskReminder = _taskReminder.asStateFlow()

    init {
        savedStateHandle.get<Boolean>(Constants.PARAM_EDITABLE)?.let {
            _editModeState.update { it }
        }

        val taskId = savedStateHandle.get<String>(Constants.PARAM_ID)

        if (!taskId.isNullOrEmpty()) {
            //todo load task from database
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

            }
            is TaskEvent.DescriptionEntered -> {

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
            is TaskEvent.ReminderSelected -> {}
            is TaskEvent.Save -> {}
            is TaskEvent.Delete -> {}
        }
    }

}