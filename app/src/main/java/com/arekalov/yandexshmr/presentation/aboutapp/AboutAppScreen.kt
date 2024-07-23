package com.arekalov.yandexshmr.presentation.aboutapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arekalov.yandexshmr.presentation.MainActivity
import com.arekalov.yandexshmr.presentation.aboutapp.views.AboutAppScreenDisplay
import com.yandex.div.core.Div2Context
import com.yandex.div.core.view2.Div2View

@Composable
fun AboutAppScreen(
    aboutAppViewModel: AboutAppViewModel = viewModel(),
    navController: NavController
) {
    val context = Div2Context(
        LocalContext.current as MainActivity,
        aboutAppViewModel.getDivConfiguration { navController.popBackStack() })
    val div2View = Div2View(context)
    val viewState = aboutAppViewModel.aboutAppViewState.collectAsState()
    AboutAppScreenDisplay(
        viewState = viewState.value,
        div2View = div2View,
    )
}