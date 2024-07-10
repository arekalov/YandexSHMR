package com.arekalov.yandexshmr.presentation.edit.models

import com.arekalov.yandexshmr.domain.model.Priority
import java.time.LocalDate

/**
All editScreen items
 **/

sealed class EditIntent {
    data object OnSaveCLick : EditIntent()
    data object BackToHome : EditIntent()
    data object OnDeleteClick : EditIntent()
    data class InitState(val itemId: String) : EditIntent()
    data class ItemTaskEdit(val task: String) : EditIntent()
    data class ItemPriorityEdit(val priority: Priority) : EditIntent()
    data class ItemDeadlineEdit(val deadline: LocalDate?) : EditIntent()
    data object ResetBackToHome : EditIntent()
}