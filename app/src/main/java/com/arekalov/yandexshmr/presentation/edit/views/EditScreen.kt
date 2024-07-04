package com.arekalov.yandexshmr.presentation.edit.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arekalov.yandexshmr.data.dto.Priority
import com.arekalov.yandexshmr.presentation.edit.EditViewModel
import com.arekalov.yandexshmr.presentation.edit.models.EditIntent
import com.arekalov.yandexshmr.presentation.edit.models.EditViewState
import java.time.LocalDate

@Composable
fun EditScreen(
    itemId: String,
    navController: NavController,
    editViewModel: EditViewModel = viewModel()
) {
    val viewState = editViewModel.editViewState.collectAsState()
    when (val state = viewState.value) {
        is EditViewState.Loading -> EditScreenLoading(
            viewState = state,
            onBack = { editViewModel.obtainIntent(intent = EditIntent.BackToHome) },
            onBackReset = { editViewModel.obtainIntent(intent = EditIntent.BackToHome) },
            goHome = { navController.popBackStack() })

        is EditViewState.Error -> EditScreenError(
            viewState = state,
            onBack = { editViewModel.obtainIntent(intent = EditIntent.BackToHome) },
            onBackReset = { editViewModel.obtainIntent(intent = EditIntent.BackToHome) },
            goHome = { navController.popBackStack() })

        is EditViewState.Display -> EditScreenDisplay(
            viewState = state,
            goHome = { navController.popBackStack() },
            onBack = { editViewModel.obtainIntent(intent = EditIntent.BackToHome) },
            onBackReset = { editViewModel.obtainIntent(intent = EditIntent.BackToHome) },
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

        else -> {}
    }
    LaunchedEffect(key1 = Unit) {
        editViewModel.obtainIntent(EditIntent.InitState(itemId))
    }
}