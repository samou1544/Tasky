package com.asma.tasky.feature_management.data.repository

import com.asma.tasky.feature_management.data.data_source.TaskyDao
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository

class TaskRepositoryImpl(private val dao: TaskyDao) : TaskRepository {



    override suspend fun getTaskById(id: Int): AgendaItem.Task? {
        return dao.getTaskById(id)
    }

    override suspend fun addTask(task: AgendaItem.Task) {
        return dao.addTask(task)
    }

    override suspend fun deleteTask(task: AgendaItem.Task) {
        return dao.deleteTask(task)
    }
}