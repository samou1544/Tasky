package com.asma.tasky.feature_management.domain.util

sealed class ModificationType(val value: String) {
    object Created : ModificationType("created")
    object Updated : ModificationType("updated")
    object Deleted : ModificationType("deleted")
}
