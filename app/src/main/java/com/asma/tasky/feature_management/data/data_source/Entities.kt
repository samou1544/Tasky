package com.asma.tasky.feature_management.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    val title: String = "",
    val description: String = "",
    val startDate: Long? = null,
    val reminder: Long? = null,
    val isDone: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)



