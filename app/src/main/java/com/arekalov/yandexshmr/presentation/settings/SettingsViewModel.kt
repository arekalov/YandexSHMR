package com.arekalov.yandexshmr.presentation.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.presentation.common.models.AppTheme
import com.arekalov.yandexshmr.presentation.settings.models.SettingsIntent
import com.arekalov.yandexshmr.presentation.settings.models.SettingsViewState
import com.arekalov.yandexshmr.presentation.settings.models.UserMark
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
            isAppLiked = UserMark.NONE
        )
    )

    init {
        initTheme()
        initUserMark()
    }

    private fun initUserMark() {
        when (sharedPreferences.getString(USER_MARK, "null")) {
            UserMark.LIKE.toString() -> _settingsViewState.value =
                _settingsViewState.value.copy(isAppLiked = UserMark.LIKE)

            UserMark.DISLIKE.toString() -> _settingsViewState.value =
                _settingsViewState.value.copy(isAppLiked = UserMark.DISLIKE)

            UserMark.NONE.toString() -> _settingsViewState.value =
                _settingsViewState.value.copy(isAppLiked = UserMark.NONE)

            else -> {
                with(this.sharedPreferences.edit()) {
                    putString(USER_MARK, UserMark.NONE.toString())
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
            is SettingsIntent.ChangeIsAppLiked -> changeIsAppLiked(currentIntent.userMark)
        }
    }

    private fun changeIsAppLiked(appLiked: UserMark) {
        _settingsViewState.value = _settingsViewState.value.copy(
            isAppLiked = appLiked
        )
        with(this.sharedPreferences.edit()) {
            putString(USER_MARK, appLiked.toString())
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