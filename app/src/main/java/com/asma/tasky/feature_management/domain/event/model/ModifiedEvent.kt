package com.asma.tasky.feature_management.domain.event.model

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.util.ModificationType

data class ModifiedEvent(
    val event: AgendaItem.Event,
    val modificationType: ModificationType
)
