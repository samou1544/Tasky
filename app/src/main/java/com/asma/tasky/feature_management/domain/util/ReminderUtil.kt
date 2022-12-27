package com.asma.tasky.feature_management.domain.util

object ReminderUtil {

     fun computeReminder(reminderTime: Long, startTime: Long): Reminder {
        return when (startTime - reminderTime) {
            Reminder.OneHourBefore.seconds -> {
                Reminder.OneHourBefore
            }
            Reminder.OneDayBefore.seconds -> {
                Reminder.OneDayBefore
            }
            Reminder.ThirtyMinutesBefore.seconds -> {
                Reminder.ThirtyMinutesBefore
            }
            Reminder.TenMinutesBefore.seconds -> {
                Reminder.TenMinutesBefore
            }
            Reminder.SixHoursBefore.seconds -> {
                Reminder.SixHoursBefore
            }
            else -> {
                Reminder.OneHourBefore
            }
        }
    }
}