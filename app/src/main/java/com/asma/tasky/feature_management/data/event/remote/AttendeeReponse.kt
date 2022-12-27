package com.asma.tasky.feature_management.data.event.remote

data class AttendeeResponse(
    val doesUserExist: Boolean,
    val attendee: Attendee
)

data class Attendee(
    val email: String,
    val fullName: String,
    val userId: String
)

fun AttendeeResponse.toAttendee(): com.asma.tasky.feature_management.domain.event.model.Attendee {
    return com.asma.tasky.feature_management.domain.event.model.Attendee(
        fullName = attendee.fullName,
        email = attendee.email,
        isCreator = false,
        isGoing = true,
        userId = attendee.userId,
        eventId = ""
    )
}