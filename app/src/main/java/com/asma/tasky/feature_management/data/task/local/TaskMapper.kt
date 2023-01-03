package com.asma.tasky.feature_management.data.task.local

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.model.ModifiedTask

fun TaskEntity.toAgendaTask(): AgendaItem.Task {
    return AgendaItem.Task(
        taskTitle = title,
        taskDescription = description,
        taskStartDate = startDate,
        taskReminder = reminder,
        isDone = isDone,
        taskId = id
    )
}

fun AgendaItem.Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        title = title,
        description = description,
        startDate = startDate,
        reminder = reminder,
        isDone = isDone,
        id = id
    )
}

fun ModifiedTask.toModifiedTaskEntity(): ModifiedTaskEntity {
    return ModifiedTaskEntity(
        title = task.title,
        description = task.description,
        startDate = task.startDate,
        reminder = task.reminder,
        isDone = task.isDone,
        id = task.id,
        modificationType = modificationType.value
    )
}
