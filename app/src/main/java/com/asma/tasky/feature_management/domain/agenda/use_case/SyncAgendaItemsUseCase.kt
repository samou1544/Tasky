package com.asma.tasky.feature_management.domain.agenda.use_case

import com.asma.tasky.feature_management.domain.agenda.repository.AgendaRepository
import javax.inject.Inject

class SyncAgendaItemsUseCase @Inject constructor(
    private val repository: AgendaRepository
) {
    operator fun invoke() {
        // todo sync agenda items
    }
}
