package com.asma.tasky.feature_management.data.event.local

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.event.model.ModifiedEvent

fun EventEntity.toAgendaEvent(): AgendaItem.Event {
    return AgendaItem.Event(
        eventTitle = title,
        eventDescription = description,
        eventStartDate = startDate,
        eventEndDate = endDate,
        eventReminder = reminder,
        eventId = id
    )
}

fun AgendaItem.Event.toEventEntity(): EventEntity {
    return EventEntity(
        title = eventTitle,
        description = eventDescription,
        startDate = eventStartDate,
        endDate = eventEndDate,
        reminder = eventReminder,
        id = eventId,
        photos = photos,
        attendees = attendees
    )
}

fun ModifiedEvent.toModifiedEventEntity(): ModifiedEventEntity {
    return ModifiedEventEntity(
        title = event.title,
        description = event.eventDescription,
        startDate = event.startDate,
        endDate = event.eventEndDate,
        reminder = event.reminder,
        id = event.id,
        modificationType = modificationType.value,
        photos = event.photos,
        attendees = event.attendees
    )
}

fun ModifiedEventEntity.toAgendaEvent(): AgendaItem.Event {
    return AgendaItem.Event(
        eventTitle = title,
        eventDescription = description,
        eventStartDate = startDate,
        eventEndDate = endDate,
        eventReminder = reminder,
        eventId = id,
        photos = photos,
        attendees = attendees
    )
}