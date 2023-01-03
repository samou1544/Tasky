package com.asma.tasky.feature_management.domain.agenda.use_case

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.agenda.repository.AgendaRepository
import com.asma.tasky.feature_management.domain.util.CacheResult
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAgendaItemsUseCase @Inject constructor(
    private val repository: AgendaRepository
) {
    operator fun invoke(day: LocalDate, cacheOnly:Boolean = false): Flow<CacheResult<List<AgendaItem>>> {
        return repository.getAgendaItems(day, cacheOnly)
    }
}
