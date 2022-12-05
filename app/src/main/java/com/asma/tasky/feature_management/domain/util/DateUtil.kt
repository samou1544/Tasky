package com.asma.tasky.feature_management.domain.util

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtil {

    fun formatDate(dateTime: LocalDateTime, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return dateTime.format(formatter)
    }

    fun secondsToLocalDateTime(seconds: Long): LocalDateTime {
        val odt = OffsetDateTime.now(ZoneId.systemDefault())
        val zoneOffset = odt.offset
        return LocalDateTime.ofEpochSecond(seconds, 0, zoneOffset)
    }

    fun localDateTimeToSeconds(dateTime: LocalDateTime): Long {
        val odt = OffsetDateTime.now(ZoneId.systemDefault())
        val zoneOffset = odt.offset
        return dateTime.toEpochSecond(zoneOffset)
    }
}
