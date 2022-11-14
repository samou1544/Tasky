package com.asma.tasky.feature_management.data.repository

import com.asma.tasky.feature_management.data.data_source.TaskyDao
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import java.time.LocalDate

class TaskRepositoryImpl(private val dao: TaskyDao) : TaskRepository {

    override suspend fun getTasksOfTheDay(day: LocalDate): List<AgendaItem.Task> {
        return dao.getTasksOfTheDay()
    }

    override suspend fun getTaskById(id: String): AgendaItem.Task? {
        return dao.getTaskById(id)
    }

    override suspend fun addTask(task: AgendaItem.Task) {
        return dao.addTask(task)
    }

    override suspend fun deleteTask(task: AgendaItem.Task) {
        return dao.deleteTask(task)
    }
}