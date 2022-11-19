package com.asma.tasky.feature_management.domain.task.use_case

import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    operator fun invoke(id: Int): Flow<Resource<AgendaItem.Task?>> = flow {
//        try {
            val result = repository.getTaskById(id)
            emit(Resource.Success(result))

//        } catch (e: java.lang.Exception) {
//            emit(Resource.Error(message = if (e.message != null) UiText.DynamicString(e.message!!) else UiText.unknownError()))
//
//        }
    }
}