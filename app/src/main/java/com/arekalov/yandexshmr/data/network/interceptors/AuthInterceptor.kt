package com.arekalov.yandexshmr.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // TODO: Вынести токен отдельно
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "OAuth y0_AgAAAABFoKeYAARC0QAAAAEJaRqeAACIG7tOI8RD04z_dB2BIIrJ0pw7FQ"
            )
            .build()
        return chain.proceed(request)
    }
}