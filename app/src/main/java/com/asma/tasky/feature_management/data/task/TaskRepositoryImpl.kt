package com.asma.tasky.feature_management.data.task

import com.asma.tasky.feature_management.data.task.local.TaskDao
import com.asma.tasky.feature_management.data.task.local.toAgendaTask
import com.asma.tasky.feature_management.data.task.local.toModifiedTaskEntity
import com.asma.tasky.feature_management.data.task.local.toTaskEntity
import com.asma.tasky.feature_management.data.task.remote.TaskApi
import com.asma.tasky.feature_management.data.task.remote.dto.toTaskDTO
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.model.ModifiedTask
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository

class TaskRepositoryImpl(private val dao: TaskDao, private val api: TaskApi) : TaskRepository {

    override suspend fun getTaskById(id: String): AgendaItem.Task? {
        return dao.getTaskById(id)?.toAgendaTask()
    }

    override suspend fun addTask(task: AgendaItem.Task) {
        dao.addTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(task: AgendaItem.Task) {
        dao.deleteTask(task.toTaskEntity())
    }

    override suspend fun getRemoteTaskById(id: String): AgendaItem.Task {
        val response = api.getTask(id)
        return response.toAgendaItem()
    }

    override suspend fun addRemoteTask(task: AgendaItem.Task) {
        api.createTask(task.toTaskDTO())
    }

    override suspend fun updateRemoteTask(task: AgendaItem.Task) {
        api.updateTask(task.toTaskDTO())
    }

    override suspend fun deleteRemoteTask(taskId: String) {
        api.deleteTask(taskId)
    }

    override suspend fun saveModifiedTask(modifiedTask: ModifiedTask) {
        dao.addModifiedTask(modifiedTask.toModifiedTaskEntity())
    }

    override suspend fun deleteModifiedTask(modifiedTask: ModifiedTask) {
        dao.deleteModifiedTask(modifiedTask.toModifiedTaskEntity())
    }
}
