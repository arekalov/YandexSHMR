package com.arekalov.yandexshmr.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class ErrorGeneratorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "X-Generate-Fails",
                "100"
            )
            .build()
        return chain.proceed(request)
    }
}