package com.arekalov.yandexshmr.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
        composable(
            route = HOME,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 250)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 250)) },
            popEnterTransition = { fadeIn(animationSpec = tween(durationMillis = 250)) },
            popExitTransition = { fadeOut(animationSpec = tween(durationMillis = 250)) }
        )
        {
            HomeScreen(
                toDoItemsViewModel = toDoItemsViewModel,
                onItemClick = { itemId: String ->
                    navController.navigate("$EDIT/$itemId")
                })
        }
        composable(
            route = EDIT_WITH_ARG,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 250)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 250)) },
            popEnterTransition = { fadeIn(animationSpec = tween(durationMillis = 250)) },
            popExitTransition = { fadeOut(animationSpec = tween(durationMillis = 250)) },
            arguments = listOf(navArgument(ITEM_ARG) { type = NavType.StringType })
        ) {
            EditScreen(
                toDoItemsViewModel = toDoItemsViewModel,
                id = it.arguments?.getString(ITEM_ARG) ?: NEW_ITEM,
                onBack = { navController.popBackStack() }
            )
        }
    }
}