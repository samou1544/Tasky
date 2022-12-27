package com.asma.tasky.feature_management.domain.reminder.repository

import com.asma.tasky.feature_management.domain.AgendaItem

interface ReminderRepository {

    suspend fun getReminderById(id: String): AgendaItem.Reminder?

    suspend fun addReminder(reminder: AgendaItem.Reminder)

    suspend fun deleteReminder(reminder: AgendaItem.Reminder)

    suspend fun getRemoteReminderById(id: String): AgendaItem.Reminder?

    suspend fun addRemoteReminder(reminder: AgendaItem.Reminder)

    suspend fun updateRemoteReminder(reminder: AgendaItem.Reminder)

    suspend fun deleteRemoteReminder(reminderId: String)
}
