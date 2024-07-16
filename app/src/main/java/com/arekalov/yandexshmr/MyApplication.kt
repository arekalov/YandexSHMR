package com.arekalov.yandexshmr

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    val authToken = BuildConfig.OAUTH_AUTHORIZATION

}