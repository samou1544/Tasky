package com.asma.tasky.feature_management.data.event

import com.asma.tasky.feature_management.data.event.local.EventDao
import com.asma.tasky.feature_management.data.event.local.toEventEntity
import com.asma.tasky.feature_management.data.event.local.toModifiedEventEntity
import com.asma.tasky.feature_management.data.event.remote.EventApi
import com.asma.tasky.feature_management.data.event.remote.toAttendee
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.event.model.Attendee
import com.asma.tasky.feature_management.domain.event.model.ModifiedEvent
import com.asma.tasky.feature_management.domain.event.repository.EventRepository

class EventRepositoryImpl(
    private val api: EventApi,
    private val dao: EventDao
) : EventRepository {
    override suspend fun getAttendee(email: String): Attendee {
        return api.getAttendee(email).toAttendee()
    }

    override suspend fun createRemoteEvent(
        event: AgendaItem.Event,
        photos: List<String>
    ): AgendaItem.Event {

        TODO("Not yet implemented")
    }

    override suspend fun updateRemoteEvent(
        event: AgendaItem.Event,
        photos: List<String>
    ): AgendaItem.Event {
        TODO("Not yet implemented")
    }

    override suspend fun insertEvent(event: AgendaItem.Event) {
        dao.addEvent(event.toEventEntity())
    }

    override suspend fun insertModifiedEvent(modifiedEvent: ModifiedEvent) {
        dao.addModifiedEvent(modifiedEvent.toModifiedEventEntity())
    }


}