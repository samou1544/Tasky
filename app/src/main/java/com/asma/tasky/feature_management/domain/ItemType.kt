package com.asma.tasky.feature_management.domain

sealed class ItemType {
    object Event : ItemType()
    object Task : ItemType()
    object Reminder : ItemType()
}
