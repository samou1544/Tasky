package com.asma.tasky.feature_management.domain.reminder.use_case

import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.reminder.repository.ReminderRepository
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

class GetReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {

    suspend operator fun invoke(id: String): Resource<AgendaItem.Reminder?> {
        val result = repository.getReminderById(id)
        try {
            val remoteReminder = repository.getRemoteReminderById(id)
            if (remoteReminder != null && remoteReminder != result) {

                if (result != null) {
                    repository.updateRemoteReminder(result)
                }
            }
            return Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()

            return Resource.Error(
                if (e.message != null) UiText.DynamicString(e.message!!)
                else UiText.unknownError()
            )
        } catch (e: HttpException) {
            e.printStackTrace()

            return Resource.Error(
                if (e.message != null) UiText.DynamicString(e.message!!)
                else UiText.unknownError()
            )
        }
    }
}
