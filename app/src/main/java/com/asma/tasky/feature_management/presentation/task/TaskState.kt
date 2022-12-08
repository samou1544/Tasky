package com.asma.tasky.feature_management.presentation.task

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.util.Reminder
import java.time.LocalDateTime

data class TaskState(
    val isLoading: Boolean = false,
    val task: AgendaItem.Task = AgendaItem.Task(),
    val showReminderDropDown: Boolean = false,
    val showDeleteTask: Boolean = false,
    val isEditable: Boolean = false,
    val taskTime: LocalDateTime = LocalDateTime.now(),
    val taskReminder: Reminder = Reminder.OneHourBefore
)
