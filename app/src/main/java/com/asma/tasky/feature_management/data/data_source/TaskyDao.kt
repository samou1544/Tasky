package com.asma.tasky.feature_management.data.data_source

import androidx.room.*
import com.asma.tasky.feature_management.domain.AgendaItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskyDao {

    @Query("SELECT * FROM task")
    fun getTasksOfTheDay(): Flow<List<AgendaItem.Task>>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id: Int): AgendaItem.Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: AgendaItem.Task)

    @Delete
    suspend fun deleteTask(task: AgendaItem.Task)

}