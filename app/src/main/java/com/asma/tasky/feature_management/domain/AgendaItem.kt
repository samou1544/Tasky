package com.asma.tasky.feature_management.domain

import java.util.*

// data class AgendaItem (
//    val title:String,
//    val description:String,
//    val startDate:Date,
//    val endDate:Date?= null,
//    val type:ItemType,
//    val isDone:Boolean = false
// )

// todo define Agenda Item as an interface
interface AgendaItem {
    val id: String
    val title: String
    val description: String
    val startDate: Long?
    val endDate: Long?
    val type: ItemType
    val isDone: Boolean
}

data class Event(
    override val title: String,
    override val description: String,
    override val startDate: Long?,
    override val endDate: Long?,
    override val type: ItemType = ItemType.Event,
    override val isDone: Boolean,
    override val id: String
) : AgendaItem

data class Task(
    override val title: String,
    override val description: String,
    override val startDate: Long?,
    override val endDate: Long? = null,
    override val type: ItemType = ItemType.Task,
    override val isDone: Boolean = false,
    override val id: String
) : AgendaItem

data class Reminder(
    override val title: String,
    override val description: String,
    override val startDate: Long?,
    override val endDate: Long? = null,
    override val type: ItemType = ItemType.Reminder,
    override val isDone: Boolean,
    override val id: String
) : AgendaItem
