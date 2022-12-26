package com.asma.tasky.feature_management.data.event

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import com.asma.tasky.feature_management.data.event.remote.CreateEventRequest
import com.asma.tasky.feature_management.data.event.remote.EventApi
import com.asma.tasky.feature_management.data.event.remote.toAttendee
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.event.model.Attendee
import com.asma.tasky.feature_management.domain.event.repository.EventRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.io.FileNotFoundException
import java.io.IOException

class EventRepositoryImpl(private val api: EventApi, private val contentResolver: ContentResolver) :
    EventRepository {

    override suspend fun getAttendee(email: String): Attendee {
        return api.getAttendee(email).toAttendee()
    }

    override suspend fun createEvent(
        event: AgendaItem.Event,
        photos: List<String>
    ): AgendaItem.Event = coroutineScope {

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
            val event = AgendaItem.Event(
                eventTitle = response.title,
                eventId = response.id,
                eventReminder = response.remindAt,
                eventStartDate = response.from,
                eventEndDate = response.to,
                eventDescription = response.description,
                eventCreator = response.host,
                photos = response.photos.map {
                    it.url
                },
                attendees = response.attendees.map {
                    Attendee(
                        name = it.fullName,
                        email = it.email,
                        isCreator = it.userId == response.host,
                        isGoing = it.isGoing,
                        userId = it.userId,
                        eventId = it.eventId
                    )
                }
            )
            event
        }
    }
}


class ContentUriRequestBody(
    private val contentResolver: ContentResolver,
    private val contentUri: Uri
) : RequestBody() {

    override fun contentType(): MediaType? {
        val contentType = contentResolver.getType(contentUri)
        return contentType?.toMediaTypeOrNull()
    }

    override fun contentLength(): Long {
        val size = contentUri.length(contentResolver)
        return size
    }

    override fun writeTo(bufferedSink: BufferedSink) {
        val inputStream = contentResolver.openInputStream(contentUri)
            ?: throw IOException("Couldn't open content URI for reading")
        inputStream.source().use { source ->
            bufferedSink.writeAll(source)
        }
    }
}

fun Uri.length(contentResolver: ContentResolver)
        : Long {

    val assetFileDescriptor = try {
        contentResolver.openAssetFileDescriptor(this, "r")
    } catch (e: FileNotFoundException) {
        null
    }
    // uses ParcelFileDescriptor#getStatSize underneath if failed
    val length = assetFileDescriptor?.use { it.length } ?: -1L
    if (length != -1L) {
        return length
    }

    // if "content://" uri scheme, try contentResolver table
    if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
        return contentResolver.query(this, arrayOf(OpenableColumns.SIZE), null, null, null)
            ?.use { cursor ->
                // maybe shouldn't trust ContentResolver for size: https://stackoverflow.com/questions/48302972/content-resolver-returns-wrong-size
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (sizeIndex == -1) {
                    return@use -1L
                }
                cursor.moveToFirst()
                return try {
                    cursor.getLong(sizeIndex)
                } catch (_: Throwable) {
                    -1L
                }
            } ?: -1L
    } else {
        return -1L
    }
}
