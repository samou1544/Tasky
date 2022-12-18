package com.asma.tasky.feature_management.presentation.event

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.event.model.Attendee

data class EventState(
    val isLoading: Boolean = false,
    val event: AgendaItem.Event = AgendaItem.Event(),
    val showReminderDropDown: Boolean = false,
    val showDeleteEvent: Boolean = false,
    val isEditable: Boolean = false,
    val attendees: List<Attendee> = emptyList()
)
