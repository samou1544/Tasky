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

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    operator fun invoke(id: Int): Flow<Resource<AgendaItem.Task?>> = flow {
        val result = repository.getTaskById(id)
        emit(Resource.Success(result))
        try {
            val remoteTask = repository.getRemoteTaskById(id)
            if(remoteTask !=null && remoteTask != result){
                //todo which decision to make: update local with remote data or update remote with local
            }
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
