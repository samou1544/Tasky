package com.asma.tasky.feature_management.domain.event.repository

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.event.model.Attendee
import com.asma.tasky.feature_management.domain.event.model.ModifiedEvent

interface EventRepository {

    suspend fun getAttendee(email: String): Attendee

    suspend fun createRemoteEvent(event: AgendaItem.Event, photos: List<String>): AgendaItem.Event

    suspend fun updateRemoteEvent(event: AgendaItem.Event, photos: List<String>): AgendaItem.Event

    suspend fun insertEvent(event: AgendaItem.Event)

    suspend fun insertModifiedEvent(modifiedEvent: ModifiedEvent)
}