package com.asma.tasky.feature_management.domain.util

sealed class ModificationType {
    object Created : ModificationType()
    object Updated : ModificationType()
    object Deleted : ModificationType()
}
