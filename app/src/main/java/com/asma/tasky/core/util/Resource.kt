package com.asma.tasky.core.util

sealed class Resource<T>(val data: T? = null, val uiText: UiText? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: UiText, data: T? = null) : Resource<T>(data, message)
}
