package com.asma.tasky.feature_management.data.event.remote

import okhttp3.MultipartBody
import retrofit2.http.*

interface EventApi {

    @GET("/attendee")
    suspend fun getAttendee(@Query("email") email: String): AttendeeResponse

    @Multipart
    @POST("/event")
    suspend fun createEvent(
        @Part eventData: MultipartBody.Part,
        @Part eventPhotos: List<MultipartBody.Part>
    ): CreateEventResponse

    companion object {
        const val BASE_URL = "https://tasky.pl-coding.com/"
    }
}
