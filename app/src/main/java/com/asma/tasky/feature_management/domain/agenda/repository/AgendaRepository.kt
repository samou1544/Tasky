package com.asma.tasky.feature_management.domain.agenda.repository

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.util.CacheResult
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface AgendaRepository {
    fun getAgendaItems(day: LocalDate, cacheOnly:Boolean): Flow<CacheResult<List<AgendaItem>>>
}
