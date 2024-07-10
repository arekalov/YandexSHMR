package com.arekalov.yandexshmr.presentation.common.models

/**
Error data class that contains data about error. Used when errors occur in Display state.
 **/
data class Error(
    val errorText: String = "Неизвестная ошибка",
    val actionText: String? = "Повторить",
    val onActionClick: (String) -> Unit
)