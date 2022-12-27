package com.asma.tasky.feature_management.domain.task.use_case

import com.asma.tasky.core.util.Resource
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.model.ModifiedTask
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import com.asma.tasky.feature_management.domain.util.ModificationType
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: AgendaItem.Task): Resource<Unit> {
        repository.deleteTask(task)
        try {
            repository.deleteRemoteTask(task.id)
        } catch (e: IOException) {
            e.printStackTrace()
            saveModifiedTask(task = task)
        } catch (e: HttpException) {
            e.printStackTrace()
            saveModifiedTask(task = task)
        }
        return Resource.Success(Unit)
    }

    private suspend fun saveModifiedTask(task: AgendaItem.Task) {
        val modifiedTask = ModifiedTask(
            task = task,
            modificationType = ModificationType.Deleted
        )
        repository.saveModifiedTask(modifiedTask)
    }
}
