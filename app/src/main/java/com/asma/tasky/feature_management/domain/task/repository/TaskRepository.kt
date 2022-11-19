package com.asma.tasky.feature_management.domain.task.repository

import com.asma.tasky.feature_management.domain.AgendaItem

interface TaskRepository {


    suspend fun getTaskById(id: Int): AgendaItem.Task?

    suspend fun addTask(task: AgendaItem.Task)

    suspend fun deleteTask(task: AgendaItem.Task)

}