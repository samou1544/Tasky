package com.asma.tasky.feature_management.data.task.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    val title: String,
    val description: String?,
    val startDate: Long,
    val reminder: Long,
    val isDone: Boolean,
    @PrimaryKey val id: String,
)

@Entity
data class ModifiedTaskEntity(
    val title: String,
    val description: String?,
    val startDate: Long,
    val reminder: Long,
    val isDone: Boolean,
    val modificationType: String,
    @PrimaryKey val id: String,
)