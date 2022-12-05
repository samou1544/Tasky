package com.asma.tasky.feature_management.domain.task.use_case

import com.asma.tasky.R
import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: AgendaItem.Task): Resource<Unit> {
        if (task.title.isBlank()) {
            return Resource.Error(message = UiText.StringResource(R.string.invalid_task))
        }
        repository.addTask(task)
        return Resource.Success(Unit)
    }
}
