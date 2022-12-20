package com.asma.tasky.feature_management.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    val title: String,
    val description: String?,
    val startDate: Long,
    val reminder: Long,
    val isDone: Boolean,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Entity
data class EventEntity(
    val title: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val reminder: Long,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Entity
data class ReminderEntity(
    val title: String,
    val description: String,
    val startDate: Long,
    val reminder: Long,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
