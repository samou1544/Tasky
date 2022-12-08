package com.asma.tasky.feature_management.data.repository

import com.asma.tasky.feature_management.data.data_source.TaskyDao
import com.asma.tasky.feature_management.data.mapper.toAgendaTask
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.agenda.repository.AgendaRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AgendaRepositoryImpl(private val dao: TaskyDao) : AgendaRepository {

    override fun getTasksOfTheDay(day: LocalDate): Flow<List<AgendaItem.Task>> {
        return dao.getTasksOfTheDay().map { tasks ->
            tasks.map {
                it.toAgendaTask()
            }
        }
    }
}