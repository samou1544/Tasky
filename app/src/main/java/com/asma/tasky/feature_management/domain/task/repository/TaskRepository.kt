package com.asma.tasky.feature_management.domain.task.repository

import com.asma.tasky.feature_management.domain.AgendaItem
import java.time.LocalDate

interface TaskRepository {

    suspend fun getTasksOfTheDay(day: LocalDate): List<AgendaItem.Task>

    suspend fun getTaskById(id: String): AgendaItem.Task?

    suspend fun addTask(task: AgendaItem.Task)

    suspend fun deleteTask(task: AgendaItem.Task)

}