package com.asma.tasky.feature_management.domain.event.repository

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.event.model.Attendee

interface EventRepository {

    suspend fun getAttendee(email: String): Attendee

    suspend fun createEvent(event: AgendaItem.Event, photos: List<String>): AgendaItem.Event

    suspend fun insertEvent(event: AgendaItem.Event)
}
