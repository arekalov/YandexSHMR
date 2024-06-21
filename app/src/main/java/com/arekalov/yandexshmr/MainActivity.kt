package com.arekalov.yandexshmr

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arekalov.yandexshmr.models.ToDoItem
import com.arekalov.yandexshmr.ui.ToDoListTheme


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<ToDoItemsViewModel>()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ToDoListTheme {
                NavHost(navController = navController, startDestination = HOME) {
                    composable(HOME)
                    {
                        HomeScreen(
                            toDoItemsViewModel = viewModel,
                            onItemClick = { item: ToDoItem -> navController.navigate("$EDIT/${item.id.value}") })
                    }
                    composable(
                        EDIT_WITH_ARG,
                        arguments = listOf(navArgument(ITEM_ARG) { type = NavType.StringType })
                    ) {
                        EditScreen(
                            toDoItemsViewModel = viewModel,
                            id = it.arguments?.getString(ITEM_ARG) ?: "",
                            onBack = { navController.navigate(HOME) }
                        )
                    }
                }
            }
        }
    }
}
