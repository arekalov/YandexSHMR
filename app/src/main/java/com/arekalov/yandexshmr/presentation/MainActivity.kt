package com.arekalov.yandexshmr.presentation

import NetworkConnectionManager
import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.arekalov.yandexshmr.BuildConfig
import com.arekalov.yandexshmr.data.network.RetrofitClient
import com.arekalov.yandexshmr.data.network.RetrofitConfig
import com.arekalov.yandexshmr.data.network.ToDoItemApi
import com.arekalov.yandexshmr.data.network.interceptors.AuthInterceptor
import com.arekalov.yandexshmr.data.network.interceptors.RetryInterceptor
import com.arekalov.yandexshmr.data.repository.ToDoItemRepositoryImpl
import com.arekalov.yandexshmr.data.workmanager.DataRefreshWorker
import com.arekalov.yandexshmr.domain.repository.ToDoItemRepository
import com.arekalov.yandexshmr.presentation.common.navigation.Navigation
import com.arekalov.yandexshmr.presentation.edit.EditViewModel
import com.arekalov.yandexshmr.presentation.edit.EditViewModelFactory
import com.arekalov.yandexshmr.presentation.home.HomeViewModel
import com.arekalov.yandexshmr.presentation.home.HomeViewModelFactory
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
App main activity start app and init components
 **/

class MainActivity : AppCompatActivity() {
    private lateinit var editViewModel: EditViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var repository: ToDoItemRepository
    private lateinit var networkConnectionManager: NetworkConnectionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = createEncryptedSharedPreferences()
        initNetworkComponents(sharedPreferences)
        initViewModels()
        initWorkManager()

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ToDoListTheme {
                Navigation(
                    navController = navController,
                    editViewModel = editViewModel,
                    homeViewModel = homeViewModel
                )
            }
        }
    }

    private fun initNetworkComponents(sharedPreferences: SharedPreferences) {
        val config = RetrofitConfig(
            baseUrl = "https://hive.mrdekk.ru/todo/",
            interceptors = listOf(
                AuthInterceptor(sharedPreferences.getString(TOKEN, "") ?: ""),
                RetryInterceptor(),
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        )
        val api = RetrofitClient.getInstance(config).create(ToDoItemApi::class.java)
        repository = ToDoItemRepositoryImpl(api)
        networkConnectionManager = NetworkConnectionManager(this)
    }

    private fun initWorkManager() {
        val workRequest = PeriodicWorkRequestBuilder<DataRefreshWorker>(8, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun initViewModels() {
        val homEViewModelFactory = HomeViewModelFactory(repository, networkConnectionManager)
        homeViewModel = ViewModelProvider(this, homEViewModelFactory).get(HomeViewModel::class.java)

        val editViewModelFactory = EditViewModelFactory(repository)
        editViewModel = ViewModelProvider(this, editViewModelFactory).get(EditViewModel::class.java)
    }

    private fun createEncryptedSharedPreferences(): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            PREF_NAME,
            masterKeyAlias,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        Log.d("!!!!!!!!!!!!!!!!!!!", "OAUTH_TOKEN: ${BuildConfig.OAUTH_TOKEN}")
        encryptedSharedPreferences.edit().putString(TOKEN, BuildConfig.OAUTH_TOKEN).apply()
        return encryptedSharedPreferences
    }

    companion object {
        const val PREF_NAME = "preferences"
        private const val TOKEN = "token"
    }
}
