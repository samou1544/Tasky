package com.asma.tasky.feature_management.presentation.agenda

import com.asma.tasky.feature_management.domain.AgendaItem
import java.time.LocalDate
import java.time.LocalDateTime

data class AgendaState(
    val isLoading: Boolean = false,
    val items: List<AgendaItem> = emptyList(),
    val selectedDate: LocalDateTime = LocalDateTime.now(),
    val selectedDay: LocalDate = LocalDate.now(),
    val userName: String = ""
)
