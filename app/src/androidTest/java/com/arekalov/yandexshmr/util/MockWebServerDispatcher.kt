package com.arekalov.yandexshmr.util

import android.util.Log
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockWebServerDispatcher {

    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            Log.e("!!!", "Request received: ${request.path}")
            Log.e("!!!", FileReader.readStringFromFile("success_response.json"))
            return when (request.path) {
                "/list" ->
                    MockResponse().setResponseCode(200)
                        .setBody(FileReader.readStringFromFile("success_response.json"))

                else -> MockResponse().setResponseCode(400)
            }
        }
    }

    internal inner class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(400)
                .setBody("error")
        }
    }
}
