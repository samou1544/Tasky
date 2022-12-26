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

    suspend operator fun invoke(task: AgendaItem.Task, newTask: Boolean = true): Resource<Unit> {
        if (task.title.isBlank()) {
            return Resource.Error(message = UiText.StringResource(R.string.invalid_task))
        }

        try {
            if (newTask) // newly created task
                repository.addRemoteTask(task)
            else repository.updateRemoteTask(task)
        } catch (e: IOException) {
            e.printStackTrace()
            //todo save as modified task for later sync
        } catch (e: HttpException) {
            e.printStackTrace()
            //todo save as modified task for later sync
        }
        return Resource.Success(Unit)
    }


}
