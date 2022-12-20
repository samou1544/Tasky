package com.asma.tasky.feature_management.data.mapper

import com.asma.tasky.feature_management.data.data_source.TaskEntity
import com.asma.tasky.feature_management.domain.AgendaItem

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
