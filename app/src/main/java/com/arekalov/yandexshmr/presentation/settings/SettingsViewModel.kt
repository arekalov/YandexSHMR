package com.arekalov.yandexshmr.presentation.settings

import androidx.lifecycle.ViewModel
import com.arekalov.yandexshmr.presentation.settings.models.AppTheme
import com.arekalov.yandexshmr.presentation.settings.models.SettingsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _settingsViewState = MutableStateFlow(
        SettingsViewState.Display(
            theme = AppTheme.SYSTEM,
            isAppLiked = false
        )
    )
    val settingsState: StateFlow<SettingsViewState>
        get() = _settingsViewState
}