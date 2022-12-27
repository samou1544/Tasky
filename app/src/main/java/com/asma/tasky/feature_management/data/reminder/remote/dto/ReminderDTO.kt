package com.asma.tasky.feature_management.data.reminder.remote.dto

import com.asma.tasky.feature_management.domain.AgendaItem

data class ReminderDTO(
    val id: String,
    val title: String,
    val description: String?,
    val time: Long,
    val remindAt: Long
) {
    fun toAgendaItem(): AgendaItem.Reminder {
        return AgendaItem.Reminder(
            reminderTitle = title,
            reminderDescription = description,
            reminderStartDate = time,
            reminderReminder = remindAt,
            reminderId = id
        )
    }
}

fun AgendaItem.Reminder.toReminderDTO(): ReminderDTO {
    return ReminderDTO(
        id = reminderId,
        title = reminderTitle,
        description = reminderDescription,
        time = reminderStartDate,
        remindAt = reminderReminder
    )
}
