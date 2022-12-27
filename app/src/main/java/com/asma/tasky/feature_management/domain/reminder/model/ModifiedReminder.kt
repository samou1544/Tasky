package com.asma.tasky.feature_management.domain.reminder.model

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.util.ModificationType

data class ModifiedReminder(
    val reminder: AgendaItem.Reminder,
    val modificationType: ModificationType
)
