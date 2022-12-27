package com.asma.tasky.feature_management.data.task.remote.dto

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
            taskId = id
        )
    }
}

fun AgendaItem.Task.toTaskDTO(): TaskDTO {
    return TaskDTO(
        id = taskId,
        title = taskTitle,
        description = taskDescription,
        time = taskStartDate,
        remindAt = taskReminder,
        isDone = isDone,
    )
}
