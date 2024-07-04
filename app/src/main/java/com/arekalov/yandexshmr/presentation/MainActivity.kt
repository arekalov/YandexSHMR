package com.arekalov.yandexshmr.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.arekalov.yandexshmr.data.repository.ToDoItemRepositoryImpl
import com.arekalov.yandexshmr.presentation.common.navigation.Navigation
import com.arekalov.yandexshmr.presentation.edit.EditViewModel
import com.arekalov.yandexshmr.presentation.edit.EditViewModelFactory
import com.arekalov.yandexshmr.presentation.home.HomeViewModel
import com.arekalov.yandexshmr.presentation.home.HomeViewModelFactory
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme


class MainActivity : AppCompatActivity() {
    private lateinit var editViewModel: EditViewModel
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ToDoItemRepositoryImpl()

        val homEViewModelFactory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, homEViewModelFactory).get(HomeViewModel::class.java)

        val editViewModelFactory = EditViewModelFactory(repository)
        editViewModel = ViewModelProvider(this, editViewModelFactory).get(EditViewModel::class.java)

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
}
