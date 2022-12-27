package com.asma.tasky.feature_management.domain.task.use_case

import com.asma.tasky.R
import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.model.ModifiedTask
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import com.asma.tasky.feature_management.domain.util.ModificationType
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: AgendaItem.Task, isNewTask: Boolean): Resource<Unit> {
        if (task.title.isBlank()) {
            return Resource.Error(message = UiText.StringResource(R.string.invalid_task))
        }
        try {
            repository.addTask(task)
            if (isNewTask)
                repository.addRemoteTask(task)
            else repository.updateRemoteTask(task)
        } catch (e: IOException) {
            e.printStackTrace()
            saveModifiedTask(task, isNewTask)
        } catch (e: HttpException) {
            e.printStackTrace()
            saveModifiedTask(task, isNewTask)
        }
        return Resource.Success(Unit)
    }


    private suspend fun saveModifiedTask(task: AgendaItem.Task, newTask: Boolean) {
        val modifiedTask = ModifiedTask(
            task = task,
            modificationType = if (newTask) ModificationType.Created else ModificationType.Updated
        )
        repository.saveModifiedTask(modifiedTask)
    }
}
