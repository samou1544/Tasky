package com.asma.tasky.feature_management.presentation.event

import android.net.Uri
import com.asma.tasky.core.domain.states.TextFieldState
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.event.model.Attendee
import com.asma.tasky.feature_management.domain.util.Reminder
import java.time.LocalDateTime

data class EventState(
    val isLoading: Boolean = false,
    val event: AgendaItem.Event = AgendaItem.Event(),
    val showReminderDropDown: Boolean = false,
    val showDeleteEvent: Boolean = false,
    val isEditable: Boolean = false,
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now().plusMinutes(30),
    val reminder: Reminder = Reminder.OneHourBefore,
    val photos: List<Uri> = emptyList(),
    val selectedAttendeeStatus: AttendeesStatus = AttendeesStatus.All,
    val showAddAttendeeDialog:Boolean = false,
    val attendeeEmail:TextFieldState = TextFieldState(),
    val isAttendeeEmailValid:Boolean? = null,
    val isCheckingAttendeeEmail:Boolean = false,
)
