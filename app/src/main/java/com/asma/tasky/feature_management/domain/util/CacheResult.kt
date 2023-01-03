package com.asma.tasky.feature_management.domain.util

import com.asma.tasky.R
import com.asma.tasky.core.util.UiText


sealed class CacheResult<out T>(
    val data: T? = null,
    val message: UiText? = null
) {

    class Local<T>(data: T): CacheResult<T>(data = data)

    class Remote<T>(data: T): CacheResult<T>(data = data)

    class Unauthorized<T>: CacheResult<T>(
        message = UiText.StringResource(R.string.error_unauthorized)
    )

    class Error<T>(
        message: UiText,
        data: T? = null
    ): CacheResult<T>(
        message = message,
        data = data
    )
}