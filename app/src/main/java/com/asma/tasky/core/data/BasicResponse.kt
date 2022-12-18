package com.asma.tasky.core.data

data class BasicResponse<T>(
    val successful: Boolean,
    val message: String? = null,
    val data: T? = null
)
