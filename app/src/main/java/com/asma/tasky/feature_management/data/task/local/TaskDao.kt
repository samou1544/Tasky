package com.asma.tasky.feature_management.data.task.local

import androidx.room.*

@Dao
interface TaskDao {

    // todo select tasks for a given day
    @Query("SELECT * FROM taskentity WHERE startDate >=:startOfDay AND startDate<=:endOfDay")
    suspend fun getTasksOfTheDay(startOfDay: Long, endOfDay: Long): List<TaskEntity>

    @Query("SELECT * FROM taskentity WHERE id = :id")
    suspend fun getTaskById(id: String): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskEntity): Long

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addModifiedTask(task: ModifiedTaskEntity): Long

    @Delete
    suspend fun deleteModifiedTask(task: ModifiedTaskEntity)

    @Query("SELECT * FROM modifiedtaskentity")
    suspend fun getModifiedTasks(): List<ModifiedTaskEntity>
}
