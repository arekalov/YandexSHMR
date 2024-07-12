package com.arekalov.yandexshmr.data.workmanager


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.arekalov.yandexshmr.domain.repository.ToDoItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
Worker help doWork after some time
 **/
class DataRefreshWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: ToDoItemRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                repository.updateToDoItemsFlow()
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}