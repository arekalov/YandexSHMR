package com.arekalov.yandexshmr.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                token
            )
            .build()
        return chain.proceed(request)
    }
}