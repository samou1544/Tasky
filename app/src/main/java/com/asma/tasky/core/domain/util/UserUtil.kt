package com.asma.tasky.core.domain.util

import java.lang.Integer.min

object UserUtil {

    fun getInitials(name: String): String {
        val trimmedName = name.trim().replace(Regex("\\s+"), " ")
        val words = trimmedName.split(" ")
        var initials = ""

        for (i in 0 until min(words.size, 2)) {
            val initial = words[i].firstOrNull()
            initial?.let {
                initials += it.toString().uppercase()
            }
        }
        return initials
    }
}
