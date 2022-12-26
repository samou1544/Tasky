package com.asma.tasky.feature_management.presentation.event

import android.net.Uri
import com.asma.tasky.feature_management.domain.event.model.Attendee
import com.asma.tasky.feature_management.domain.util.Reminder
import java.time.LocalDate
import java.time.LocalTime

sealed class EventEvent {
    data class TitleEntered(val title: String) : EventEvent()
    data class AttendeeEmailEntered(val email: String) : EventEvent()

    data class AttendeeEmailAdded(val email: String) : EventEvent()

    data class AttendeeRemoved(val attendee: Attendee) : EventEvent()
    data class DescriptionEntered(val description: String) : EventEvent()
    data class StartTimeSelected(val time: LocalTime) : EventEvent()
    data class StartDateSelected(val date: LocalDate) : EventEvent()

    data class EndTimeSelected(val time: LocalTime) : EventEvent()
    data class EndDateSelected(val date: LocalDate) : EventEvent()
    data class ReminderSelected(val reminder: Reminder) : EventEvent()
    data class PhotosAdded(val uriList: List<Uri>) : EventEvent()
    data class PhotoDeleted(val uri: Uri) : EventEvent()
    data class ChangeStatus(val status: AttendeesStatus) : EventEvent()
    object ToggleReminderDropDown : EventEvent()
    object ToggleShowAddAttendeeDialog : EventEvent()
    object ToggleEditMode : EventEvent()
    object Save : EventEvent()
    object Delete : EventEvent()
}
