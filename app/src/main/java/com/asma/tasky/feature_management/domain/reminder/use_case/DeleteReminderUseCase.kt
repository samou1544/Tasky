package com.asma.tasky.feature_management.domain.reminder.use_case

import com.asma.tasky.core.util.Resource
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.reminder.repository.ReminderRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {

    suspend operator fun invoke(reminder: AgendaItem.Reminder): Resource<Unit> {
        repository.deleteReminder(reminder)
        try {
            repository.deleteRemoteReminder(reminder.id)
        } catch (e: IOException) {
            e.printStackTrace()
            saveModifiedReminder(reminder = reminder)
        } catch (e: HttpException) {
            e.printStackTrace()
            saveModifiedReminder(reminder = reminder)
        }
        return Resource.Success(Unit)

    }

    private suspend fun saveModifiedReminder(reminder: AgendaItem.Reminder) {
//        val modifiedReminder = ModifiedReminder(
//            reminder = reminder,
//            modificationType = ModificationType.Deleted
//        )
//        repository.saveModifiedReminder(modifiedReminder)
    }

}
