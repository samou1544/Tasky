package com.asma.tasky.core.util

import android.content.Context
import androidx.annotation.StringRes
import com.asma.tasky.R

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(@StringRes val id: Int) : UiText()

    companion object {
        fun unknownError(): UiText {
            return StringResource(R.string.unknown_error)
        }
    }
}

fun UiText.asString(context: Context): String {
    return when(this) {
        is UiText.DynamicString -> this.value
        is UiText.StringResource -> context.getString(this.id)
    }
}
