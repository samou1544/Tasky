package com.asma.tasky.feature_management.presentation.task

import com.asma.tasky.feature_management.domain.AgendaItem

data class TaskState(
    val isLoading: Boolean = false,
    val task: AgendaItem.Task = AgendaItem.Task(),
    val showReminderDropDown: Boolean = false,
    val showDeleteTask:Boolean = false
)
