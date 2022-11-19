package com.asma.tasky.feature_management.domain.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtil {

    fun formatDate(dateTime: LocalDateTime, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return dateTime.format(formatter)
    }
}