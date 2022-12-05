package com.asma.tasky.feature_management.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class AgendaItem {
    abstract val title: String
    abstract val description: String
    abstract val startDate: Long?
    abstract val reminder: Long?

    data class Task(
        override val title: String = "",
        override val description: String = "",
        override val startDate: Long? = null,
        override val reminder: Long? = null,
        val isDone: Boolean = false,
        var id: Int = 0
    ) : AgendaItem()

    
    @Entity
    data class Event(
        override val title: String = "",
        override val description: String = "",
        override val startDate: Long? = null,
        val endDate: Long? = null,
        override val reminder: Long? = null,
        @PrimaryKey(autoGenerate = true) var id: Int = 0
    ) : AgendaItem()

    @Entity
    data class Reminder(
        override val title: String = "",
        override val description: String = "",
        override val startDate: Long? = null,
        override val reminder: Long? = null,
        @PrimaryKey(autoGenerate = true) var id: Int = 0
    ) : AgendaItem()
}
