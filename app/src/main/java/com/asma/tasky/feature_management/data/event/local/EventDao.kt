package com.asma.tasky.feature_management.data.event.local

import androidx.room.*

@Dao
interface EventDao {

    // todo select tasks for a given day
    @Query("SELECT * FROM evententity WHERE startDate >=:startOfDay AND startDate<=:endOfDay")
    suspend fun getEventsOfTheDay(startOfDay: Long, endOfDay: Long): List<EventEntity>

    @Query("SELECT * FROM evententity WHERE id = :id")
    suspend fun getEventById(id: String): EventEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvent(event: EventEntity): Long

    @Delete
    suspend fun deleteEvent(event: EventEntity)


    @Query("SELECT * FROM modifiedevententity")
    suspend fun getModifiedEvents(): List<ModifiedEventEntity>

}