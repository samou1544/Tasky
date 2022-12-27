package com.asma.tasky.feature_management.data.reminder.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ReminderEntity(
    val title: String,
    val description: String?,
    val startDate: Long,
    val reminder: Long,
    @PrimaryKey val id: String
)

@Entity
data class ModifiedReminderEntity(
    val title: String,
    val description: String?,
    val startDate: Long,
    val reminder: Long,
    val modificationType: String,
    @PrimaryKey val id: String,
)