package com.asma.tasky.feature_management.presentation.event

sealed class Attendees(val value: String) {
    object All : Attendees("All")
    object Going : Attendees("Going")
    object NotGoing : Attendees("Not going")
}
