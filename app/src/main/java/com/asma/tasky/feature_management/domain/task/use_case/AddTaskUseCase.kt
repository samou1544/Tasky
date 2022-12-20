package com.asma.tasky.feature_management.domain.task.use_case

import com.asma.tasky.R
import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.model.ModifiedTask
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import com.asma.tasky.feature_management.domain.util.ModificationType
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: AgendaItem.Task): Resource<Unit> {
        if (task.title.isBlank()) {
            return Resource.Error(message = UiText.StringResource(R.string.invalid_task))
        }
        val rowId = repository.addTask(task)

        try {
            if (task.id == 0) // newly created task
                repository.addRemoteTask(task.copy(taskId = rowId.toInt()))
            else repository.updateRemoteTask(task)
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
            modificationType = ModificationType.Created.value,
            id = task.id
        )
        repository.saveModifiedTask(modifiedTask)
    }
}
