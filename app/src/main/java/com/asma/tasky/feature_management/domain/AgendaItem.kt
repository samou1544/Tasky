package com.asma.tasky.feature_management.domain

import android.os.Parcelable
import com.asma.tasky.feature_management.domain.event.model.Attendee
import kotlinx.parcelize.Parcelize
import java.util.UUID


sealed class AgendaItem(
    val id: String,
    val title: String,
    val description: String?,
    val startDate: Long,
    val reminder: Long,
) : Parcelable {
    @Parcelize data class Task(
        val taskTitle: String = "",
        val taskDescription: String? = "",
        val taskStartDate: Long = System.currentTimeMillis(),
        val taskReminder: Long = System.currentTimeMillis(),
        val isDone: Boolean = false,
        var taskId: String = UUID.randomUUID().toString()
    ) : AgendaItem(
        id = taskId,
        title = taskTitle,
        description = taskDescription,
        startDate = taskStartDate,
        reminder = taskReminder
    )

    @Parcelize  data class Event(
         val eventTitle: String = "",
         val eventDescription: String? = null,
         val eventStartDate: Long = System.currentTimeMillis(),
        val eventEndDate: Long = System.currentTimeMillis(),
         val eventReminder: Long = System.currentTimeMillis(),
         val eventId: String = UUID.randomUUID().toString(),
         val eventCreator:String = "",
         val photos:List<String> = emptyList(),
         val attendees:List<Attendee> = emptyList()
    ) : AgendaItem(
        id = eventId,
        title = eventTitle,
        description = eventDescription,
        startDate = eventStartDate,
        reminder = eventReminder
    )

    @Parcelize  data class Reminder(
         val reminderTitle: String = "",
         val reminderDescription: String = "",
         val reminderStartDate: Long = System.currentTimeMillis(),
         val reminderReminder: Long = System.currentTimeMillis(),
         var reminderId: String = UUID.randomUUID().toString()
    ) : AgendaItem(
        id = reminderId,
        title = reminderTitle,
        description = reminderDescription,
        startDate = reminderStartDate,
        reminder = reminderReminder
    )
}
