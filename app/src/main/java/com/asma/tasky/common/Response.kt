package com.asma.tasky.common

data class Response<T>(
    val successful: Boolean,
    val message: String? = null,
    val data: T? = null
)