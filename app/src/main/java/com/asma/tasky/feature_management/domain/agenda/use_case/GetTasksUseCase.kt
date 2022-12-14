package com.asma.tasky.feature_management.domain.agenda.use_case

import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.agenda.repository.AgendaRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: AgendaRepository
) {
    operator fun invoke(day: LocalDate): Flow<List<AgendaItem.Task>> {
        return repository.getTasksOfTheDay(day)
    }
}
