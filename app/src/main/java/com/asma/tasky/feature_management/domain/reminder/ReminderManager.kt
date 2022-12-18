package com.asma.tasky.feature_management.domain.reminder

import androidx.work.*
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.util.DateUtil
import com.google.gson.Gson
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderManager @Inject constructor(
    private val workManager: WorkManager
) {

    fun startReminderWorker(agendaItem: AgendaItem) {

        val serializedAgendaItem = Gson().toJson(agendaItem)
        val inputData = workDataOf("agendaItem" to serializedAgendaItem)
//        val inputData = when (agendaItem) {
//            is AgendaItem.Task -> {
//                workDataOf(
//                    "itemId" to agendaItem.id,
//                    "itemType" to "task"
//                )
//            }
//            is AgendaItem.Event -> {
//                workDataOf(
//                    "itemId" to agendaItem.id,
//                    "itemType" to "event"
//                )
//            }
//            is AgendaItem.Reminder -> {
//                workDataOf(
//                    "itemId" to agendaItem.id,
//                    "itemType" to "reminder"
//                )
//            }
//        }
        val currentTime = LocalDateTime.now()
        val delay = (agendaItem.reminder - DateUtil.localDateTimeToSeconds(currentTime))
        if (delay > 0) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build()
            val work = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(delay, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .setInputData(
                    inputData
                )
                .build()

            workManager.enqueueUniqueWork(
                "${agendaItem.id}${agendaItem.javaClass}",
                ExistingWorkPolicy.REPLACE,
                work
            )

        }

    }

    fun cancelReminderWorker(agendaItem: AgendaItem) {
        val uniqueWorkName = "${agendaItem.id}${agendaItem.javaClass}"
        workManager.cancelUniqueWork(uniqueWorkName)
    }
}