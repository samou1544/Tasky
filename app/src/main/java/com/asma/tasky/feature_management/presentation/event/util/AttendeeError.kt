package com.asma.tasky.feature_management.presentation.event.util

sealed class AttendeeError : com.asma.tasky.core.util.Error() {
    object InvalidEmail : AttendeeError()
    object NoUserFound : AttendeeError()
}
