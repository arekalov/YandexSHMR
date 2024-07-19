package com.arekalov.yandexshmr.presentation.common.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.arekalov.yandexshmr.presentation.edit.EditScreen
import com.arekalov.yandexshmr.presentation.edit.EditViewModel
import com.arekalov.yandexshmr.presentation.home.HomeScreen
import com.arekalov.yandexshmr.presentation.home.HomeViewModel
import com.arekalov.yandexshmr.presentation.settings.SettingsScreen
import com.arekalov.yandexshmr.presentation.settings.SettingsViewModel
import com.arekalov.yandexshmr.presentation.settings.aboutscreen.AboutAppScreen
import com.arekalov.yandexshmr.presentation.settings.models.SettingsIntent


@Composable
fun Navigation(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
    editViewModel: EditViewModel,
    homeViewModel: HomeViewModel,
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
                homeViwModel = homeViewModel,
                navController = navController,
            )
        }
        composable(
            route = SETTINGS,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 250)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 250)) },
            popEnterTransition = { fadeIn(animationSpec = tween(durationMillis = 250)) },
            popExitTransition = { fadeOut(animationSpec = tween(durationMillis = 250)) }
        )
        {
            SettingsScreen(
                settingsViewModel = settingsViewModel,
                navController = navController,
            )
        }
        composable(
            route = ABOUT,
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 250)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 250)) },
            popEnterTransition = { fadeIn(animationSpec = tween(durationMillis = 250)) },
            popExitTransition = { fadeOut(animationSpec = tween(durationMillis = 250)) }
        )
        {
            AboutAppScreen(
                settingsViewState = settingsViewModel.settingsState,
                onIsAppLikedChanged = { value: Boolean ->
                    settingsViewModel.obtainIntent(
                        SettingsIntent.ChangeIsAppLiked(value)
                    )
                },
                onBackClicked = { navController.popBackStack() }
            )
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
                editViewModel = editViewModel,
                itemId = it.arguments?.getString(ITEM_ARG) ?: NEW_ITEM,
                navController = navController
            )
        }
    }
}