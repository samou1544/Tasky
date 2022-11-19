package com.asma.tasky.feature_management.domain.util

import androidx.annotation.StringRes
import com.asma.tasky.R

sealed class Reminder(@StringRes val value: Int, val seconds: Long) {
    object TenMinutesBefore : Reminder(R.string.ten_minutes_before, 600)
    object ThirtyMinutesBefore : Reminder(R.string.thirty_minutes_before, 1800)
    object OneHourBefore : Reminder(R.string.one_hour_before, 3600)
    object SixHoursBefore : Reminder(R.string.six_hours_before, 21600)
    object OneDayBefore : Reminder(R.string.one_day_before, 86400)
}
