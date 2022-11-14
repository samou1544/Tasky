package com.asma.tasky.feature_management.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class AgendaItem {
    @Entity
    data class Task(
        val title: String = "",
        val description: String = "",
        val startDate: Long? = null,
        val reminder: Long? = null,
        val isDone: Boolean = false,
        @PrimaryKey val id: String? = null
    ) : AgendaItem()

    @Entity
    data class Event(
        val title: String = "",
        val description: String = "",
        val startDate: Long? = null,
        val endDate: Long? = null,
        val isDone: Boolean = false,
        @PrimaryKey val id: String? = null
    ) : AgendaItem()

    @Entity
    data class Reminder(
        val title: String = "",
        val description: String = "",
        val startDate: Long? = null,
        @PrimaryKey val id: String? = null
    ) : AgendaItem()

}