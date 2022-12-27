package com.asma.tasky.feature_management.domain.event.use_case

import com.asma.tasky.R
import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.event.repository.EventRepository
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val repository: EventRepository
) {

    suspend operator fun invoke(event: AgendaItem.Event, photos: List<String>): Resource<Unit> {
        if (event.title.isEmpty())
            return Resource.Error(message = UiText.StringResource(R.string.invalid_event))

        return try {

            repository.insertEvent(event)

            val response = repository.createEvent(event = event, photos = photos)

            repository.insertEvent(response)

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(message = UiText.DynamicString(e.message!!))
        }
    }
}
