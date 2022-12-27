package com.asma.tasky.feature_management.data.reminder.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    // todo select tasks for a given day
    @Query("SELECT * FROM reminderentity WHERE startDate >=:startOfDay AND startDate<=:endOfDay")
    fun getRemindersOfTheDay(startOfDay: Long, endOfDay: Long): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminderentity WHERE id = :id")
    suspend fun getReminderById(id: String): ReminderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReminder(reminder: ReminderEntity): Long

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)


    @Query("SELECT * FROM modifiedreminderentity")
    fun getModifiedReminders(): Flow<List<ModifiedReminderEntity>>
}
