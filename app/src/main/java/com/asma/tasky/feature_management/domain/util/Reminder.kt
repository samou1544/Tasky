package com.asma.tasky.feature_management.domain.util

import androidx.annotation.StringRes
import com.asma.tasky.R

sealed class Reminder(@StringRes val value: Int) {
    object TenMinutesBefore : Reminder(R.string.ten_minutes_before)
    object ThirtyMinutesBefore : Reminder(R.string.thirty_minutes_before)
    object OneHourBefore : Reminder(R.string.one_hour_before)
    object SixHoursBefore : Reminder(R.string.six_hours_before)
    object OneDayBefore : Reminder(R.string.one_day_before)
}
