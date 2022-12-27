package com.asma.tasky.feature_management.data.reminder.local

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.reminder.model.ModifiedReminder

fun ReminderEntity.toAgendaReminder(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        reminderTitle = title,
        reminderDescription = description,
        reminderStartDate = startDate,
        reminderReminder = reminder,
        reminderId = id
    )
}

fun AgendaItem.Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        title = title,
        description = description,
        startDate = startDate,
        reminder = reminder,
        id = id
    )
}

fun ModifiedReminder.toModifiedReminderEntity(): ModifiedReminderEntity {
    return ModifiedReminderEntity(
        title = reminder.title,
        description = reminder.description,
        startDate = reminder.startDate,
        reminder = reminder.reminder,
        id = reminder.id,
        modificationType = modificationType.value
    )
}
