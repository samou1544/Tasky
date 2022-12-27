package com.asma.tasky.feature_management.presentation.agenda

import com.asma.tasky.feature_management.domain.AgendaItem
import java.time.LocalDate

sealed class AgendaEvent {
    data class DateSelected(val date: LocalDate) : AgendaEvent()
    data class DaySelected(val day: LocalDate) : AgendaEvent()
    data class ToggleTaskIsDone(val task: AgendaItem.Task) : AgendaEvent()
    data class DeleteItem(val item: AgendaItem) : AgendaEvent()

//    object Delete : AgendaEvent()
}
