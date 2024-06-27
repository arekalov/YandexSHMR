package com.arekalov.yandexshmr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.arekalov.yandexshmr.ToDoItemsViewModel
import com.arekalov.yandexshmr.screens.EditScreen
import com.arekalov.yandexshmr.screens.HomeScreen

@Composable
fun Navigation(
    navController: NavHostController,
    toDoItemsViewModel: ToDoItemsViewModel,
) {
    NavHost(navController = navController, startDestination = HOME) {
        composable(HOME)
        {
            println("openHome")
            HomeScreen(
                toDoItemsViewModel = toDoItemsViewModel,
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
                toDoItemsViewModel = toDoItemsViewModel,
                id = it.arguments?.getString(ITEM_ARG) ?: NEW_ITEM,
                onBack = { navController.popBackStack() }
            )
        }
    }
}