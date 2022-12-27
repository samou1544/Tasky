package com.asma.tasky.feature_management.data.reminder

import com.asma.tasky.feature_management.data.reminder.local.ReminderDao
import com.asma.tasky.feature_management.data.reminder.local.toAgendaReminder
import com.asma.tasky.feature_management.data.reminder.local.toReminderEntity
import com.asma.tasky.feature_management.data.reminder.remote.ReminderApi
import com.asma.tasky.feature_management.data.reminder.remote.dto.toReminderDTO
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.reminder.repository.ReminderRepository

class ReminderRepositoryImpl(
    private val dao: ReminderDao,
    private val api: ReminderApi
) : ReminderRepository {

    override suspend fun getReminderById(id: String): AgendaItem.Reminder? {
        return dao.getReminderById(id)?.toAgendaReminder()
    }

    override suspend fun addReminder(reminder: AgendaItem.Reminder) {
        dao.addReminder(reminder = reminder.toReminderEntity())
    }

    override suspend fun deleteReminder(reminder: AgendaItem.Reminder) {
        dao.deleteReminder(reminder = reminder.toReminderEntity())
    }

    override suspend fun getRemoteReminderById(id: String): AgendaItem.Reminder? {
        return api.getReminder(id).toAgendaItem()
    }

    override suspend fun addRemoteReminder(reminder: AgendaItem.Reminder) {
        api.createReminder(reminder.toReminderDTO())
    }

    override suspend fun updateRemoteReminder(reminder: AgendaItem.Reminder) {
        api.updateReminder(reminder.toReminderDTO())
    }

    override suspend fun deleteRemoteReminder(reminderId: String) {
        api.deleteReminder(reminderId)
    }
}