package com.asma.tasky.feature_management.data.repository

import com.asma.tasky.feature_management.data.data_source.TaskyDao
import com.asma.tasky.feature_management.data.mapper.toAgendaTask
import com.asma.tasky.feature_management.data.mapper.toTaskEntity
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.agenda.repository.AgendaRepository
import com.asma.tasky.feature_management.domain.util.DateUtil
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AgendaRepositoryImpl(private val dao: TaskyDao) : AgendaRepository {

    override fun getTasksOfTheDay(day: LocalDate): Flow<List<AgendaItem.Task>> {
        val endOfDay = day.atStartOfDay().with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay())
        return dao.getTasksOfTheDay(
            startOfDay = DateUtil.localDateTimeToSeconds(day.atStartOfDay()),
            endOfDay = DateUtil.localDateTimeToSeconds(endOfDay)
        ).map { tasks ->
            tasks.map {
                it.toAgendaTask()
            }
        }
    }
}
