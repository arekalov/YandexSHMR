package com.arekalov.yandexshmr.presentation.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.presentation.common.models.AppTheme
import com.arekalov.yandexshmr.presentation.settings.models.SettingsIntent
import com.arekalov.yandexshmr.presentation.settings.models.SettingsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

const val THEME = "theme"

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val myContext: Context,
) : ViewModel() {
    private val sharedPreferences =
        myContext.getSharedPreferences(myContext.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val _settingsViewState = MutableStateFlow(
        SettingsViewState(
            theme = AppTheme.SYSTEM
        )
    )

    val settingsState: StateFlow<SettingsViewState>
        get() = _settingsViewState

    init {
        initTheme()
    }

    private fun initTheme() {
        when (sharedPreferences.getString(THEME, "null")) {
            AppTheme.SYSTEM.toString() -> _settingsViewState.value =
                _settingsViewState.value.copy(theme = AppTheme.SYSTEM)

            AppTheme.DARK.toString() -> _settingsViewState.value =
                _settingsViewState.value.copy(theme = AppTheme.DARK)

            AppTheme.LIGHT.toString() -> _settingsViewState.value =
                _settingsViewState.value.copy(theme = AppTheme.LIGHT)

            else -> {
                with(this.sharedPreferences.edit()) {
                    putString(THEME, AppTheme.SYSTEM.toString())
                    apply()
                }
            }
        }
    }

    fun obtainIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.ChangeTheme -> changeTheme(intent.theme)
        }
    }

    private fun changeTheme(newTheme: AppTheme) {
        _settingsViewState.value = _settingsViewState.value.copy(
            theme = newTheme
        )
        with(this.sharedPreferences.edit()) {
            putString(THEME, newTheme.toString())
            apply()
        }
    }
}