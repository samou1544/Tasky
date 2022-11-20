package com.asma.tasky.feature_management.domain.task.use_case

import com.asma.tasky.core.util.Resource
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    operator fun invoke(task: AgendaItem.Task): Flow<Resource<Unit>> = flow {
        repository.deleteTask(task)
        emit(Resource.Success(Unit))
    }
}
