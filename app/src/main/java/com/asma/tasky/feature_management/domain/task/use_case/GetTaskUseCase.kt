package com.asma.tasky.feature_management.domain.task.use_case

import com.asma.tasky.core.util.Resource
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(id: Int): Resource<AgendaItem.Task?> {
        val result = repository.getTaskById(id)
        return Resource.Success(result)
    }
}
