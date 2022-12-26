package com.asma.tasky.feature_management.domain.task.repository

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.model.ModifiedTask

interface TaskRepository {

    suspend fun getTaskById(id: String): AgendaItem.Task?

    suspend fun addTask(task: AgendaItem.Task)

    suspend fun deleteTask(task: AgendaItem.Task)

    suspend fun getRemoteTaskById(id: String): AgendaItem.Task?

    suspend fun addRemoteTask(task: AgendaItem.Task)

    suspend fun updateRemoteTask(task: AgendaItem.Task)

    suspend fun deleteRemoteTask(taskId: String)

    suspend fun saveModifiedTask(modifiedTask: ModifiedTask)
}
