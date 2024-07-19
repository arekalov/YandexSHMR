package com.arekalov.yandexshmr.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.arekalov.yandexshmr.data.workmanager.DataRefreshWorker
import com.arekalov.yandexshmr.presentation.aboutapp.AboutAppViewModel
import com.arekalov.yandexshmr.presentation.common.navigation.Navigation
import com.arekalov.yandexshmr.presentation.edit.EditViewModel
import com.arekalov.yandexshmr.presentation.home.HomeViewModel
import com.arekalov.yandexshmr.presentation.settings.SettingsViewModel
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


/**
App main activity start app and init components
 **/

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val editViewModel: EditViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val aboutAppViewModel: AboutAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWorkManager()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val theme = settingsViewModel.settingsState.collectAsState()
            ToDoListTheme(theme = theme.value.theme) {
                Navigation(
                    navController = navController,
                    editViewModel = editViewModel,
                    homeViewModel = homeViewModel,
                    settingsViewModel = settingsViewModel,
                    aboutAppViewModel = aboutAppViewModel
                )
            }
        }
    }

    private fun initWorkManager() {
        val workRequest = PeriodicWorkRequestBuilder<DataRefreshWorker>(8, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
}
