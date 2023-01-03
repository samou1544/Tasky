package com.asma.tasky.feature_management.domain.reminder.use_case

import com.asma.tasky.R
import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.reminder.repository.ReminderRepository
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

class AddReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {

    suspend operator fun invoke(
        reminder: AgendaItem.Reminder,
        isNewReminder: Boolean
    ): Resource<Unit> {
        if (reminder.title.isBlank()) {
            return Resource.Error(message = UiText.StringResource(R.string.invalid_Reminder))
        }
        try {
            repository.addReminder(reminder)
            if (isNewReminder)
                repository.addRemoteReminder(reminder)
            else repository.updateRemoteReminder(reminder)
        } catch (e: IOException) {
            e.printStackTrace()
            saveModifiedReminder(reminder, isNewReminder)
        } catch (e: HttpException) {
            e.printStackTrace()
            saveModifiedReminder(reminder, isNewReminder)
        }
        return Resource.Success(Unit)
    }

    private suspend fun saveModifiedReminder(reminder: AgendaItem.Reminder, newReminder: Boolean) {
//        val modifiedReminder = ModifiedReminder(
//            reminder = reminder,
//            modificationType = if (newReminder) ModificationType.Created else ModificationType.Updated
//        )
//        repository.saveModifiedReminder(modifiedReminder)
    }
}
