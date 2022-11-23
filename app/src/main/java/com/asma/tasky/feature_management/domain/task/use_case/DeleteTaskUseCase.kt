package com.asma.tasky.feature_management.domain.task.use_case

import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    operator fun invoke(task: AgendaItem.Task): Flow<Resource<Unit>> = flow {
        repository.deleteTask(task)
        emit(Resource.Success(Unit))
        try {
            repository.deleteRemoteTask(task.id.toString())
        } catch (e: IOException) {
            e.printStackTrace()
            emit(
                Resource.Error(
                    if (e.message != null) UiText.DynamicString(e.message!!)
                    else UiText.unknownError()
                )
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(
                Resource.Error(
                    if (e.message != null) UiText.DynamicString(e.message!!)
                    else UiText.unknownError()
                )
            )
        }
    }
}
