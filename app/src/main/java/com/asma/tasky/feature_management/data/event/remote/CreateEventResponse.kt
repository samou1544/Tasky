package com.asma.tasky.feature_management.data.event.remote


data class CreateEventResponse(
    val id:String,
    val title:String,
    val description:String?,
    val from:Long,
    val to:Long,
    val remindAt: Long,
    val host:String,
    val isUserEventCreator:Boolean,
    val attendees:List<EventResponseAttendee>,
    val photos:List<EventResponsePhoto>
)

data class EventResponseAttendee(
    val email:String,
    val fullName:String,
    val userId:String,
    val eventId:String,
    val isGoing:Boolean,
    val remindAt:Long
)

data class EventResponsePhoto(
    val key:String,
    val url:String
)