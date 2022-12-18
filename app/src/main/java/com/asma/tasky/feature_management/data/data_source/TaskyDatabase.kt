package com.asma.tasky.feature_management.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TaskEntity::class,
        ModifiedTask::class
    ],
    version = 1
)
abstract class TaskyDatabase : RoomDatabase() {

    abstract val taskyDao: TaskyDao

    companion object {
        const val DATABASE_NAME = "tasky_db"
    }
}
