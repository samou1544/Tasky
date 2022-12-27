package com.asma.tasky.feature_management.domain.event.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attendee(
    val fullName: String,
    val email: String,
    val isCreator: Boolean,
    val isGoing: Boolean = false,
    val userId: String,
    val eventId: String
) : Parcelable
