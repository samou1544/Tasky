package com.asma.tasky.feature_management.data.remote

import com.asma.tasky.core.data.BasicResponse
import com.asma.tasky.feature_management.data.remote.dto.TaskDTO
import retrofit2.Response
import retrofit2.http.*

interface TaskApi {

    @POST("/task")
    suspend fun createTask(
        @Body request: TaskDTO
    ): BasicResponse<Unit>

    @PUT("/task")
    suspend fun updateTask(
        @Body request: TaskDTO
    ): BasicResponse<Unit>

    @GET("/task")
    suspend fun getTask(@Query("taskId") taskId: String): Response<TaskDTO>

    @DELETE("/task")
    suspend fun deleteTask(
        @Query("taskId") taskId: String
    )

    companion object {
        const val BASE_URL = "https://tasky.pl-coding.com/"
    }
}
