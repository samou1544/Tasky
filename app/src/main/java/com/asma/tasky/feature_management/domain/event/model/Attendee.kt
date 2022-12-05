package com.asma.tasky.feature_management.domain.event.model

data class Attendee(
    val name: String,
    val email: String,
    val isCreator: Boolean,
    val isGoing: Boolean = false
)
