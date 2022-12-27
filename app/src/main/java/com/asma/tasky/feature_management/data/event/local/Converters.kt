package com.asma.tasky.feature_management.data.event.local

import androidx.room.TypeConverter
import com.asma.tasky.feature_management.domain.event.model.Attendee
import com.asma.tasky.feature_management.domain.event.model.Photo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class Converters {

    @TypeConverter
    fun attendeeListToJson(value: List<Attendee>?): String {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Array<Attendee>> = moshi.adapter(Array<Attendee>::class.java)
        return adapter.toJson(value?.toTypedArray())
    }

    @TypeConverter
    fun jsonToAttendeeList(value: String): List<Attendee>? {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Array<Attendee>> = moshi.adapter(Array<Attendee>::class.java)
        return adapter.fromJson(value)?.toList()
    }

    @TypeConverter
    fun photoListToJson(value: List<Photo>?): String {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Array<Photo>> = moshi.adapter(Array<Photo>::class.java)
        return adapter.toJson(value?.toTypedArray())
    }

    @TypeConverter
    fun jsonToPhotoList(value: String): List<Photo>? {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Array<Photo>> = moshi.adapter(Array<Photo>::class.java)
        return adapter.fromJson(value)?.toList()
    }
}
