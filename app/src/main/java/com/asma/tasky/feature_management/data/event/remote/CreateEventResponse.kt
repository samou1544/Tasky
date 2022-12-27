package com.asma.tasky.feature_management.data.event.remote

import com.asma.tasky.feature_management.domain.event.model.Photo


data class CreateEventResponse(
    val id:String,
    val title:String,
    val description:String,
    val from:Long,
    val to:Long,
    val remindAt: Long,
    val host:String,
    val isUserEventCreator:Boolean,
    val attendees:List<EventResponseAttendee>,
    val photos:List<Photo>
)

data class EventResponseAttendee(
    val email:String,
    val fullName:String,
    val userId:String,
    val eventId:String,
    val isGoing:Boolean,
    val remindAt:Long
)

