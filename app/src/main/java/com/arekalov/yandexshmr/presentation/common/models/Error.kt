package com.arekalov.yandexshmr.presentation.common.models

data class Error(
    val errorText: String = "Неизвестная ошибка",
    val actionText: String? = "Повторить",
    val onActionClick: (String) -> Unit
)