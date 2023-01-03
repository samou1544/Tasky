package com.asma.tasky.feature_management.domain.task.use_case

import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(id: String): Resource<AgendaItem.Task?> {
        val result = repository.getTaskById(id)
        try {
            val remoteTask = repository.getRemoteTaskById(id)
            if (remoteTask != null && remoteTask != result) {

                if (result != null) {
                    repository.updateRemoteTask(result)
                }
            }
            return Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()

            return Resource.Error(
                if (e.message != null) UiText.DynamicString(e.message!!)
                else UiText.unknownError()
            )
        } catch (e: HttpException) {
            e.printStackTrace()

            return Resource.Error(
                if (e.message != null) UiText.DynamicString(e.message!!)
                else UiText.unknownError()
            )
        }
    }
}
