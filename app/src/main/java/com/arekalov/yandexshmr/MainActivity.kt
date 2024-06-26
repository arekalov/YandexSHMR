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
                        println("openHome")
                        HomeScreen(
                            toDoItemsViewModel = viewModel,
                            onItemClick = { itemId: String ->
                                navController.navigate("$EDIT/$itemId")
                            })
                    }
                    composable(
                        EDIT_WITH_ARG,
                        arguments = listOf(navArgument(ITEM_ARG) { type = NavType.StringType })
                    ) {
                        println("openEdit")
                        EditScreen(
                            toDoItemsViewModel = viewModel,
                            id = it.arguments?.getString(ITEM_ARG) ?: NEW_ITEM,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
