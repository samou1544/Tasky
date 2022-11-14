package com.asma.tasky.feature_management.domain.task.use_case

import com.asma.tasky.R
import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddTaskUseCase(private val repository: TaskRepository) {

    suspend operator fun invoke(task: AgendaItem.Task): Flow<Resource<Unit>> = flow {
        if (task.title.isBlank()) {
            emit(Resource.Error(message = UiText.StringResource(R.string.invalid_task)))
        }
        repository.addTask(task)
        emit(Resource.Success(Unit))
    }
}