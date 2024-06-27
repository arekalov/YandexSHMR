package com.arekalov.yandexshmr

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.arekalov.yandexshmr.navigation.Navigation
import com.arekalov.yandexshmr.ui.ToDoListTheme


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<ToDoItemsViewModel>()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ToDoListTheme {
                Navigation(navController = navController, toDoItemsViewModel = viewModel)
            }
        }
    }
}
