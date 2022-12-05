package com.asma.tasky.feature_management.domain.task.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModifiedTask(
    val title: String,
    val description: String?,
    val startDate: Long,
    val reminder: Long,
    val isDone: Boolean,
    val modificationType: String,
    @PrimaryKey var id: Int,
)
