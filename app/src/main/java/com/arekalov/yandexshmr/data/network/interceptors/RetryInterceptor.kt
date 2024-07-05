package com.arekalov.yandexshmr.data.network.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(
    private val maxRetry: Int = 5,
    private val retryIntervalMillis: Long = 1000
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        var response: Response? = null
        var retryCount = 0

        while (retryCount < maxRetry) {
            try {
                response = chain.proceed(request)
                if (response.isSuccessful || !shouldRetry(response.code)) {
                    return response
                } else {
                    Log.i(
                        "RetryInterceptor",
                        "Retry attempt $retryCount for response code ${response.code}"
                    )
                }
            } catch (e: IOException) {
                if (retryCount >= maxRetry - 1) {
                    throw e
                }
                Log.i("RetryInterceptor", "IOException on attempt $retryCount: ${e.message}")
            }

            retryCount++
            try {
                Thread.sleep(retryIntervalMillis)
            } catch (interruptedException: InterruptedException) {
                Thread.currentThread().interrupt()
                throw IOException("Retry interrupted", interruptedException)
            }
        }

        return response ?: throw IOException("Max retries reached")
    }

    private fun shouldRetry(responseCode: Int): Boolean {
        return when (responseCode) {
            500, 502, 503, 504 -> true
            else -> false
        }
    }
}
