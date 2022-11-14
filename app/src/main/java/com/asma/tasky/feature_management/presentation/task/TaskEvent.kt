package com.asma.tasky.feature_management.presentation.task

import com.asma.tasky.feature_management.domain.util.Reminder
import java.time.LocalDate
import java.time.LocalTime

sealed class TaskEvent {
    data class TitleEntered(val title: String) : TaskEvent()
    data class DescriptionEntered(val description: String) : TaskEvent()
    data class TimeSelected(val time: LocalTime) : TaskEvent()
    data class DateSelected(val date: LocalDate) : TaskEvent()
    data class ReminderSelected(val reminder: Reminder) : TaskEvent()
    object ToggleReminderDropDown : TaskEvent()
    object Save : TaskEvent()
    object Delete : TaskEvent()
}
