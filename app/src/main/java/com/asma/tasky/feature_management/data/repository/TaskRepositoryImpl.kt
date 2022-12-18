package com.asma.tasky.feature_management.data.repository

import com.asma.tasky.feature_management.data.data_source.TaskyDao
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.model.ModifiedTask
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository

class TaskRepositoryImpl(private val dao: TaskyDao, private val api: TaskApi) : TaskRepository {

    override suspend fun getTaskById(id: Int): AgendaItem.Task? {
        return dao.getTaskById(id)?.toAgendaTask()
    }

    override suspend fun addTask(task: AgendaItem.Task) {
        return dao.addTask(task)
    }

    override suspend fun deleteTask(task: AgendaItem.Task) {
        return dao.deleteTask(task.toTaskEntity())
    }

    override suspend fun getRemoteTaskById(id: Int): AgendaItem.Task? {
        val response = api.getTask(id.toString())
        return response.body()?.toAgendaItem()
    }

    override suspend fun addRemoteTask(task: AgendaItem.Task) {
        val taskDTO = TaskDTO(
            id = task.id.toString(),
            title = task.title,
            description = task.description,
            time = task.startDate,
            remindAt = task.reminder,
            isDone = task.isDone,
        )
        api.createTask(taskDTO)
    }

    override suspend fun updateRemoteTask(task: AgendaItem.Task) {
        val taskDTO = TaskDTO(
            id = task.id.toString(),
            title = task.title,
            description = task.description,
            time = task.startDate,
            remindAt = task.reminder,
            isDone = task.isDone,
        )
        api.updateTask(taskDTO)
    }

    override suspend fun deleteRemoteTask(taskId: String) {
        api.deleteTask(taskId)
    }

    override suspend fun saveModifiedTask(modifiedTask: ModifiedTask) {
        TODO("Not yet implemented")
    }
}
