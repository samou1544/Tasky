package com.asma.tasky.feature_management.data.event

import androidx.work.*
import javax.inject.Inject

class EventUploader @Inject constructor(
    private val workManager: WorkManager
) {
    fun startEventWorker(request: String, type: String) {

        val inputData = workDataOf(
            "event_request" to request,
            "type" to type
        )
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val work = OneTimeWorkRequestBuilder<EventWorker>()
            .setConstraints(constraints)
            .setInputData(
                inputData
            )
            .build()

        workManager.enqueue(work)

    }
}