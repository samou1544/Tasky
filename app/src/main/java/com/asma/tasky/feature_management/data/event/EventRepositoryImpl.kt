package com.asma.tasky.feature_management.data.event

import android.content.ContentResolver
import android.net.Uri
import com.asma.tasky.feature_management.data.event.local.EventDao
import com.asma.tasky.feature_management.data.event.local.toEventEntity
import com.asma.tasky.feature_management.data.event.local.toModifiedEventEntity
import com.asma.tasky.feature_management.data.event.remote.CreateEventRequest
import com.asma.tasky.feature_management.data.event.remote.EventApi
import com.asma.tasky.feature_management.data.event.remote.toAttendee
import com.asma.tasky.feature_management.data.event.util.ContentUriRequestBody
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.event.model.Attendee
import com.asma.tasky.feature_management.domain.event.model.ModifiedEvent
import com.asma.tasky.feature_management.domain.event.repository.EventRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class EventRepositoryImpl(
    private val api: EventApi,
    private val contentResolver: ContentResolver,
    private val eventUploader: EventUploader,
    private val dao: EventDao
) : EventRepository {
    override suspend fun getAttendee(email: String): Attendee {
        return api.getAttendee(email).toAttendee()
    }

    override suspend fun createRemoteEvent(
        event: AgendaItem.Event,
        photos: List<String>
    ): AgendaItem.Event = coroutineScope {

        //event uploader should start the eventUpload worker

        val request = CreateEventRequest(
            id = event.eventId,
            title = event.eventTitle,
            description = event.eventDescription,
            from = event.eventStartDate,
            to = event.eventEndDate,
            remindAt = event.eventReminder,
            attendeeIds = event.attendees.map {
                it.userId
            }
        )

        eventUploader.startEventWorker(Gson().toJson(request), type = "create")


        val parts: List<MultipartBody.Part> = photos.map {
            val uri = Uri.parse(it)
            val requestFile = ContentUriRequestBody(contentResolver, uri)
            MultipartBody.Part
                .createFormData(
                    name = "photo",
                    filename = "test",
                    body = requestFile
                )
        }
        withContext(Dispatchers.IO) {
            val response = api.createEvent(
                eventData = MultipartBody.Part
                    .createFormData(
                        "create_event_request",
                        Gson().toJson(request)
                    ),
                eventPhotos = parts

            )
            val result = AgendaItem.Event(
                eventTitle = response.title,
                eventId = response.id,
                eventReminder = response.remindAt,
                eventStartDate = response.from,
                eventEndDate = response.to,
                eventDescription = response.description,
                eventCreator = response.host,
                photos = response.photos,
                attendees = response.attendees.map {
                    Attendee(
                        fullName = it.fullName,
                        email = it.email,
                        isCreator = it.userId == response.host,
                        isGoing = it.isGoing,
                        userId = it.userId,
                        eventId = it.eventId
                    )
                }
            )
            result
        }
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