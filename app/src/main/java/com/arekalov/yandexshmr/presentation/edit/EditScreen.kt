package com.arekalov.yandexshmr.presentation.edit

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.presentation.edit.models.EditIntent
import com.arekalov.yandexshmr.presentation.edit.models.EditViewState
import com.arekalov.yandexshmr.presentation.edit.views.EditScreenDisplay
import com.arekalov.yandexshmr.presentation.edit.views.EditScreenLoading
import java.time.LocalDate

@Composable
fun EditScreen(
    itemId: String,
    navController: NavController,
    editViewModel: EditViewModel = viewModel()
) {
    BackHandler {
        editViewModel.obtainIntent(EditIntent.BackToHome)
    }
    val viewState = editViewModel.editViewState.collectAsState()
    when (val state = viewState.value) {
        is EditViewState.Loading -> EditScreenLoading(
            viewState = state,
            onBack = { editViewModel.obtainIntent(intent = EditIntent.BackToHome) },
            onBackReset = { editViewModel.obtainIntent(intent = EditIntent.ResetBackToHome) },
            goHome = { navController.popBackStack() })

        is EditViewState.Display -> EditScreenDisplay(
            viewState = state,
            goHome = { navController.popBackStack() },
            onBack = { editViewModel.obtainIntent(intent = EditIntent.BackToHome) },
            onBackReset = { editViewModel.obtainIntent(intent = EditIntent.ResetBackToHome) },
            onSaveClick = { editViewModel.obtainIntent(intent = EditIntent.OnSaveCLick) },
            onTaskChange = { task: String ->
                editViewModel.obtainIntent(
                    intent = EditIntent.ItemTaskEdit(
                        task
                    )
                )
            },
            onDeleteClick = { editViewModel.obtainIntent(intent = EditIntent.OnDeleteClick) },
            onDeadlineChange = { deadline: LocalDate? ->
                editViewModel.obtainIntent(intent = EditIntent.ItemDeadlineEdit(deadline))
            },
            onPriorityChange = { priority: Priority ->
                editViewModel.obtainIntent(
                    intent = EditIntent.ItemPriorityEdit(
                        priority
                    )
                )
            }
        )
    }
    LaunchedEffect(key1 = Unit) {
        editViewModel.obtainIntent(EditIntent.InitState(itemId))
    }
}