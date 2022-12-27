package com.asma.tasky.feature_management.data.reminder.remote

import com.asma.tasky.feature_management.data.reminder.remote.dto.ReminderDTO
import retrofit2.http.*

interface ReminderApi {

    @POST("/reminder")
    suspend fun createReminder(
        @Body request: ReminderDTO
    )

    @PUT("/reminder")
    suspend fun updateReminder(
        @Body request: ReminderDTO
    )

    @GET("/reminder")
    suspend fun getReminder(@Query("reminderId") reminderId: String): ReminderDTO

    @DELETE("/reminder")
    suspend fun deleteReminder(
        @Query("reminderId") reminderId: String
    )

    companion object {
        const val BASE_URL = "https://tasky.pl-coding.com/"
    }
}
