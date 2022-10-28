package com.asma.tasky.authentication.data.remote.util

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()

            .addHeader("Authorization", "Bearer 10895044a3d34ddba7fa77f292d145fe")
            .build()
        return chain.proceed(newRequest)

    }
}