package com.asma.tasky.feature_management.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.asma.tasky.feature_management.data.event.local.Converters
import com.asma.tasky.feature_management.data.event.local.EventDao
import com.asma.tasky.feature_management.data.event.local.EventEntity
import com.asma.tasky.feature_management.data.event.local.ModifiedEventEntity
import com.asma.tasky.feature_management.data.reminder.local.ModifiedReminderEntity
import com.asma.tasky.feature_management.data.reminder.local.ReminderDao
import com.asma.tasky.feature_management.data.reminder.local.ReminderEntity
import com.asma.tasky.feature_management.data.task.local.ModifiedTaskEntity
import com.asma.tasky.feature_management.data.task.local.TaskDao
import com.asma.tasky.feature_management.data.task.local.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        ModifiedTaskEntity::class,
        EventEntity::class,
        ModifiedEventEntity::class,
        ReminderEntity::class,
        ModifiedReminderEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class TaskyDatabase : RoomDatabase() {

    abstract val taskyDao: TaskDao

    abstract val eventDao: EventDao

    abstract val reminderDao: ReminderDao

    companion object {
        const val DATABASE_NAME = "tasky_db"
    }
}
