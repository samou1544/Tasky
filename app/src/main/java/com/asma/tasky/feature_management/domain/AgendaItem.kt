package com.asma.tasky.feature_management.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class AgendaItem {
    abstract val title: String
    abstract val description: String?
    abstract val startDate: Long

    @Entity
    data class Task(
        override val title: String = "",
        override val description: String? = null,
        override val startDate: Long = System.currentTimeMillis(),
        val reminder: Long = System.currentTimeMillis(),
        val isDone: Boolean = false,
        @PrimaryKey(autoGenerate = true) var id: Int = 0
    ) : AgendaItem()

    @Entity
    data class Event(
        override val title: String = "",
        override val description: String? = null,
        override val startDate: Long = System.currentTimeMillis(),
        val endDate: Long? = null,
        val isDone: Boolean = false,
        @PrimaryKey(autoGenerate = true) var id: Int = 0
    ) : AgendaItem()

    @Entity
    data class Reminder(
        override val title: String = "",
        override val description: String? = null,
        override val startDate: Long = System.currentTimeMillis(),
        @PrimaryKey(autoGenerate = true) var id: Int = 0
    ) : AgendaItem()
}
