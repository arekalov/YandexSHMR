package com.arekalov.yandexshmr.data.workmanager


import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleDataRefreshWork(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
        .build()

    val refreshWorkRequest = PeriodicWorkRequestBuilder<DataRefreshWorker>(8, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "DataRefreshWork",
        ExistingPeriodicWorkPolicy.KEEP,
        refreshWorkRequest
    )
}