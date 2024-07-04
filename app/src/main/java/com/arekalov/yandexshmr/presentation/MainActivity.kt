package com.arekalov.yandexshmr.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.arekalov.yandexshmr.presentation.common.navigation.Navigation
import com.arekalov.yandexshmr.presentation.home.HomeViewModel
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toDoItemsViewModel by viewModels<ToDoItemsViewModel>()
        val homeVIewModel by viewModels<HomeViewModel>()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ToDoListTheme {
                Navigation(
                    navController = navController,
                    toDoItemsViewModel = toDoItemsViewModel,
                    homeViewModel = homeVIewModel
                )
            }
        }
    }
}
