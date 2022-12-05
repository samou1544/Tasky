package com.asma.tasky.core.domain.util

object Util {

    fun getInitials(name: String): String {
        val words = name.split(" ")
        var initials = ""
        words.forEach { word ->
            initials += word.first().toString().uppercase()
        }
        return initials
    }
}