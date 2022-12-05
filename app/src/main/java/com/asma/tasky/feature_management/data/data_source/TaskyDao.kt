package com.asma.tasky.feature_management.data.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskyDao {

    // todo select tasks for a given day
    @Query("SELECT * FROM taskentity")
    fun getTasksOfTheDay(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM taskentity WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}
