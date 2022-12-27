package com.asma.tasky.feature_management.data.event.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asma.tasky.feature_management.domain.event.model.Attendee
import com.asma.tasky.feature_management.domain.event.model.Photo

@Entity
data class EventEntity(
    val title: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val reminder: Long,
    val photos: List<Photo>,
    val attendees: List<Attendee>,
    @PrimaryKey val id: String
)

@Entity
data class ModifiedEventEntity(
    val title: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val reminder: Long,
    val photos: List<Photo>,
    val attendees: List<Attendee>,
    val modificationType: String,
    @PrimaryKey val id: String,
)