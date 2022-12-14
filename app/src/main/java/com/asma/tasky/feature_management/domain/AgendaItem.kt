package com.asma.tasky.feature_management.domain


sealed class AgendaItem {
    abstract val title: String
    abstract val description: String
    abstract val startDate: Long
    abstract val reminder: Long

    data class Task(
        override val title: String = "",
        override val description: String = "",
        override val startDate: Long = System.currentTimeMillis(),
        override val reminder: Long = System.currentTimeMillis(),
        val isDone: Boolean = false,
        var id: Int = 0
    ) : AgendaItem()

    data class Event(
        override val title: String = "",
        override val description: String = "",
        override val startDate: Long = System.currentTimeMillis(),
        val endDate: Long = System.currentTimeMillis(),
        override val reminder: Long = System.currentTimeMillis(),
        var id: Int = 0
    ) : AgendaItem()

    data class Reminder(
        override val title: String = "",
        override val description: String = "",
        override val startDate: Long = System.currentTimeMillis(),
        override val reminder: Long = System.currentTimeMillis(),
        var id: Int = 0
    ) : AgendaItem()
}
