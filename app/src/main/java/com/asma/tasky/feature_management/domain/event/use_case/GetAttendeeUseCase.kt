package com.asma.tasky.feature_management.domain.event.use_case

import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.event.model.Attendee
import com.asma.tasky.feature_management.domain.event.repository.EventRepository
import javax.inject.Inject

class GetAttendeeUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(email: String): Resource<Attendee> {
        return try {
            Resource.Success(repository.getAttendee(email))
        } catch (e: Exception) {
            Resource.Error(
                message = if (e.message != null) UiText.DynamicString(e.message!!)
                else UiText.unknownError()
            )
        }
    }
}
