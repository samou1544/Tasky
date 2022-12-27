package com.asma.tasky.feature_management.domain.task.model

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.util.ModificationType


data class ModifiedTask(
    val task: AgendaItem.Task,
    val modificationType: ModificationType,

)
