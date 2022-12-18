package com.asma.tasky.feature_management.presentation.event

import com.asma.tasky.feature_management.domain.util.Reminder
import java.time.LocalDate
import java.time.LocalTime

sealed class EventEvent {
    data class TitleEntered(val title: String) : EventEvent()
    data class DescriptionEntered(val description: String) : EventEvent()
    data class TimeSelected(val time: LocalTime) : EventEvent()
    data class DateSelected(val date: LocalDate) : EventEvent()
    data class ReminderSelected(val reminder: Reminder) : EventEvent()
    data class PhotosAdded(val uriList: List<String>) : EventEvent()
    data class PhotoDeleted(val uri: String) : EventEvent()
    object ToggleReminderDropDown : EventEvent()
    object ToggleEditMode : EventEvent()
    object Save : EventEvent()
    object Delete : EventEvent()
}
