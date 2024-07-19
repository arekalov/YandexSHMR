package com.arekalov.yandexshmr.presentation.aboutapp

import android.content.Context
import androidx.lifecycle.ViewModel
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.presentation.aboutapp.models.AboutAppIntent
import com.arekalov.yandexshmr.presentation.aboutapp.models.AboutAppViewState
import com.arekalov.yandexshmr.presentation.common.models.AppTheme
import com.arekalov.yandexshmr.presentation.settings.THEME
import com.yandex.div.core.DivConfiguration
import com.yandex.div.glide.GlideDivImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

const val IS_APP_LIKED = "isAppLiked"

@HiltViewModel
class AboutAppViewModel @Inject constructor(
    @ApplicationContext private val myContext: Context,
) : ViewModel() {
    private val sharedPreferences =
        myContext.getSharedPreferences(myContext.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val _aboutAppViewState = MutableStateFlow(
        AboutAppViewState(
            isAppLiked = false,
            theme = AppTheme.DARK
        )
    )
    val aboutAppViewState: StateFlow<AboutAppViewState>
        get() = _aboutAppViewState

    init {
        initIsAppLiked()
        initTheme()
    }

    private fun initTheme() {
        when (sharedPreferences.getString(THEME, "null")) {
            AppTheme.SYSTEM.toString() -> _aboutAppViewState.value =
                _aboutAppViewState.value.copy(theme = AppTheme.SYSTEM)

            AppTheme.DARK.toString() -> _aboutAppViewState.value =
                _aboutAppViewState.value.copy(theme = AppTheme.DARK)

            AppTheme.LIGHT.toString() -> _aboutAppViewState.value =
                _aboutAppViewState.value.copy(theme = AppTheme.LIGHT)

            else -> {
                with(this.sharedPreferences.edit()) {
                    putString(THEME, AppTheme.SYSTEM.toString())
                    apply()
                }
            }
        }
    }

    private fun initIsAppLiked() {
        when (sharedPreferences.getString(IS_APP_LIKED, "null")) {
            "false" -> _aboutAppViewState.value =
                _aboutAppViewState.value.copy(isAppLiked = false)

            "true" -> _aboutAppViewState.value =
                _aboutAppViewState.value.copy(isAppLiked = true)

            else -> {
                with(this.sharedPreferences.edit()) {
                    putString(IS_APP_LIKED, "false")
                    apply()
                }
            }
        }
    }

    fun obtainIntent(intent: AboutAppIntent) {
        when (intent) {
            is AboutAppIntent.ChangeIsAppLiked -> changeIsAppLiked(intent.isAppLiked)
            is AboutAppIntent.UpdateTheme -> initTheme()
        }
    }

    private fun changeIsAppLiked(isAppLiked: Boolean) {
        _aboutAppViewState.value = _aboutAppViewState.value.copy(
            isAppLiked = isAppLiked
        )
        with(this.sharedPreferences.edit()) {
            putString(IS_APP_LIKED, isAppLiked.toString())
            apply()
        }
    }

    fun getDivConfiguration(onBack: () -> Unit): DivConfiguration {
        val divActionHandler = MyDivActionHandler(
            onBackClick = onBack,
            changeLike = { isAppLiked: Boolean -> changeIsAppLiked(isAppLiked) }
        )
        return DivConfiguration.Builder(GlideDivImageLoader(myContext))
            .actionHandler(divActionHandler)
            .visualErrorsEnabled(true)
            .build()

    }
}