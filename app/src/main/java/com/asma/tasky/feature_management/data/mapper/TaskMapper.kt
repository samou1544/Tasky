package com.asma.tasky.feature_management.data.mapper

import com.asma.tasky.feature_management.data.data_source.TaskEntity
import com.asma.tasky.feature_management.domain.AgendaItem

fun TaskEntity.toAgendaItem(): AgendaItem.Task {
    return AgendaItem.Task(
        title = title,
        description = description,
        startDate = startDate,
        reminder = reminder,
        isDone = isDone,
        id = id
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

