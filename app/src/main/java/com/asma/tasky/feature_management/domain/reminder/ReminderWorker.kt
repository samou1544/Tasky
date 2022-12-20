package com.asma.tasky.feature_management.domain.reminder

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.notification_service.ReminderNotificationService
import com.asma.tasky.feature_management.domain.task.use_case.GetTaskUseCase
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
    val getTaskUseCase: GetTaskUseCase
) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val service = ReminderNotificationService(context)
        //this throws an error
        val agendaItem = Gson().fromJson(inputData.getString("agendaItem"), AgendaItem::class.java)
        if (agendaItem != null) {
            service.showNotification(agendaItem)
        }
//        val id = inputData.getInt("itemId", -1)
//        val type = inputData.getString("itemType")
//        if (id != -1) {
//            when (type) {
//                "task" -> {
//                    when (val result = getTaskUseCase(id)) {
//                        is Resource.Success -> {
//                            result.data?.let { task ->
//                                service.showNotification(agendaItem = task)
//                            }
//                        }
//                        is Resource.Error -> {}
//                    }
//                }
//                "event" -> {
//
//                }
//                "reminder" -> {
//
//                }
//
//            }
//        }
        return Result.success()
    }
}



