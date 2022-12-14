package com.asma.tasky.core.domain.reminder

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.asma.tasky.core.domain.notification_service.ReminderNotificationService
import com.asma.tasky.core.util.Resource
import com.asma.tasky.feature_management.domain.task.use_case.GetTaskUseCase
import dagger.assisted.Assisted

@HiltWorker
class ReminderWorker(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getTaskUseCase: GetTaskUseCase
) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val service = ReminderNotificationService(context)
        val id = inputData.getInt("itemId", -1)
        val type = inputData.getString("itemType")
        if (id != -1) {
            when (type) {
                "task" -> {
                    when (val result = getTaskUseCase(id)) {
                        is Resource.Success -> {
                            result.data?.let { task ->
                                service.showNotification(agendaItem = task)
                            }
                        }
                        is Resource.Error -> {}
                    }
                }
                "event" -> {

                }
                "reminder" -> {

                }

            }
        }
        return Result.success()
    }
}