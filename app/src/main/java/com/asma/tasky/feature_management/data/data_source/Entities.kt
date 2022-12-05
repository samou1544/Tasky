package com.asma.tasky.feature_management.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    val title: String = "",
    val description: String = "",
    val startDate: Long = System.currentTimeMillis(),
    val reminder: Long? = null,
    val isDone: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Entity
data class Event(
    val title: String = "",
    val description: String = "",
    val startDate: Long? = null,
    val endDate: Long = System.currentTimeMillis(),
    val reminder: Long? = null,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)

@Entity
data class Reminder(
    val title: String = "",
    val description: String = "",
    val startDate: Long = System.currentTimeMillis(),
    val reminder: Long? = null,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
