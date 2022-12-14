package com.asma.tasky.feature_management.domain.agenda.repository

import com.asma.tasky.feature_management.domain.AgendaItem
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface AgendaRepository {

    fun getTasksOfTheDay(day: LocalDate): Flow<List<AgendaItem.Task>>

}
