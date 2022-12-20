package com.asma.tasky.feature_management.domain.task.use_case

import com.asma.tasky.core.util.Resource
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.model.ModifiedTask
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import com.asma.tasky.feature_management.domain.util.ModificationType
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: AgendaItem.Task): Resource<Unit> {
        repository.deleteTask(task)
        try {
            repository.deleteRemoteTask(task.id.toString())
        } catch (e: IOException) {
            e.printStackTrace()
            addModifiedTask(task)

        } catch (e: HttpException) {
            e.printStackTrace()
            addModifiedTask(task)
        }
        return Resource.Success(Unit)

    }

    private suspend fun addModifiedTask(task: AgendaItem.Task) {
        val modifiedTask = ModifiedTask(
            title = task.title,
            description = task.description,
            startDate = task.startDate,
            reminder = task.reminder,
            isDone = task.isDone,
            modificationType = ModificationType.Deleted.value,
            id = task.id
        )
        repository.saveModifiedTask(modifiedTask)
    }
}
