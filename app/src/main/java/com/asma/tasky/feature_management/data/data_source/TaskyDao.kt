package com.asma.tasky.feature_management.data.data_source

import androidx.room.*
import com.asma.tasky.feature_management.domain.AgendaItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskyDao {

    // todo select tasks for a given day
    @Query("SELECT * FROM taskentity WHERE startDate >=:startOfDay AND startDate<=:endOfDay")
    fun getTasksOfTheDay(startOfDay: Long, endOfDay: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM taskentity WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: AgendaItem.Task): Long

    @Delete
    suspend fun deleteTask(task: AgendaItem.Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addModifiedTask(task: ModifiedTask): Long

    @Delete
    suspend fun deleteModifiedTask(task: ModifiedTask)

    @Query("SELECT * FROM modifiedtask")
    fun getModifiedTasks(): Flow<List<ModifiedTask>>
}
