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

const val USER_MARK = "userMark"
const val THEME = "theme"

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val myContext: Context,
) : ViewModel() {
    private val sharedPreferences =
        myContext.getSharedPreferences(myContext.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val _settingsViewState = MutableStateFlow(
        SettingsViewState(
            theme = AppTheme.SYSTEM,
            isAppLiked = false
        )
    )

    init {
        initTheme()
        initUserMark()
    }

    private fun initUserMark() {
        when (sharedPreferences.getString(USER_MARK, "null")) {
            "false" -> _settingsViewState.value =
                _settingsViewState.value.copy(isAppLiked = false)

            "true" -> _settingsViewState.value =
                _settingsViewState.value.copy(isAppLiked = true)

            else -> {
                with(this.sharedPreferences.edit()) {
                    putString(USER_MARK, "false")
                    apply()
                }
            }
        }
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

    val settingsState: StateFlow<SettingsViewState>
        get() = _settingsViewState

    fun obtainIntent(intent: SettingsIntent) {
        when (val currentIntent = intent) {
            is SettingsIntent.ChangeTheme -> changeTheme(currentIntent.theme)
            is SettingsIntent.ChangeIsAppLiked -> changeIsAppLiked(currentIntent.isAppLiked)
        }
    }

    private fun changeIsAppLiked(isAppLiked: Boolean) {
        _settingsViewState.value = _settingsViewState.value.copy(
            isAppLiked = isAppLiked
        )
        with(this.sharedPreferences.edit()) {
            putString(USER_MARK, isAppLiked.toString())
            apply()
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