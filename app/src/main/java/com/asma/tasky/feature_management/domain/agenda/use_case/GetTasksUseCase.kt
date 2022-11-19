package com.asma.tasky.feature_management.domain.agenda.use_case

import com.asma.tasky.core.util.Resource
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.agenda.repository.AgendaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: AgendaRepository
) {

    operator fun invoke(): Flow<List<AgendaItem.Task>> {
        return repository.getTasksOfTheDay(LocalDate.now())
    }
//    = flow {
//        try {
//            val result = repository.getTasksOfTheDay(LocalDate.now())
//            emit(Resource.Success(result))
//        } catch (e: java.lang.Exception) {
//            emit(Resource.Error(message = if (e.message != null) UiText.DynamicString(e.message!!) else UiText.unknownError()))
//        }
//
//    }
}