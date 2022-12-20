package com.asma.tasky.feature_management.data.remote.dto

import com.asma.tasky.feature_management.domain.AgendaItem

data class TaskDTO(
    val id: String,
    val title: String,
    val description: String?,
    val time: Long,
    val remindAt: Long,
    val isDone: Boolean
) {
    fun toAgendaItem(): AgendaItem.Task {
        return AgendaItem.Task(
            taskTitle = title,
            taskDescription = description,
            taskStartDate = time,
            taskReminder = remindAt,
            isDone = isDone,
            taskId = id.toInt()
        )
    }
}
