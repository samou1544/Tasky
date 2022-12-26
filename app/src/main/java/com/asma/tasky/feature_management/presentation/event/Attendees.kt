package com.asma.tasky.feature_management.presentation.event

sealed class AttendeesStatus(val value: String) {
    object All : AttendeesStatus("All")
    object Going : AttendeesStatus("Going")
    object NotGoing : AttendeesStatus("Not going")
}
