package com.asma.tasky.feature_management.data.task.remote

import com.asma.tasky.feature_management.data.task.remote.dto.TaskDTO
import retrofit2.http.*

interface TaskApi {

    @POST("/task")
    suspend fun createTask(
        @Body request: TaskDTO
    )

    @PUT("/task")
    suspend fun updateTask(
        @Body request: TaskDTO
    )

    @GET("/task")
    suspend fun getTask(@Query("taskId") taskId: String): TaskDTO

    @DELETE("/task")
    suspend fun deleteTask(
        @Query("taskId") taskId: String
    )

    companion object {
        const val BASE_URL = "https://tasky.pl-coding.com/"
    }
}
