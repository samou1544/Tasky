package com.asma.tasky.feature_management.presentation.agenda

import com.asma.tasky.feature_management.domain.AgendaItem

data class AgendaState(
    val isLoading: Boolean = false,
    val items: List<AgendaItem> = emptyList(),
)
