package com.asma.tasky.feature_management.domain.agenda.repository

import com.asma.tasky.feature_management.domain.AgendaItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AgendaRepository {

    fun getTasksOfTheDay(day: LocalDate): Flow<List<AgendaItem.Task>>

}