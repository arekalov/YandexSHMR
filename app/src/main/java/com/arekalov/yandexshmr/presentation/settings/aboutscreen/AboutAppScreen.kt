package com.arekalov.yandexshmr.presentation.settings.aboutscreen

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.arekalov.yandexshmr.presentation.MainActivity
import com.arekalov.yandexshmr.presentation.common.models.AppTheme
import com.arekalov.yandexshmr.presentation.settings.models.SettingsViewState
import com.yandex.div.DivDataTag
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.view2.Div2View
import com.yandex.div.data.DivParsingEnvironment
import com.yandex.div.glide.GlideDivImageLoader
import com.yandex.div.json.ParsingErrorLogger
import com.yandex.div2.DivData
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject

@Composable
fun AboutAppScreen(
    settingsViewState: StateFlow<SettingsViewState>,
    onIsAppLikedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {
    val viewState = settingsViewState.collectAsState()
    val themeNow = viewState.value.theme
    val context = LocalContext.current as MainActivity
    val config = createDivConfiguration(
        onIsAppLikedChanged = onIsAppLikedChanged,
        onBackClicked = onBackClicked,
        context = context
    )
    val theme = if (themeNow == AppTheme.LIGHT) {
        "light"
    } else if (themeNow == AppTheme.DARK) {
        "dark"
    } else {
        if (isSystemInDarkTheme()) "dark" else "light"
    }
    val divData = JSONObject(getJson(theme, viewState.value.isAppLiked)).asDiv2DataWithTemplates()
    val div2context = Div2Context(context, config)
    val div2View = Div2View(div2context)
    div2View.setData(divData, DivDataTag("your_unique_tag_here"))
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { ctx ->
            Div2View(Div2Context(context, config)).apply {
                setData(divData, DivDataTag("your_unique_tag_here"))
            }
        }
    )
}

fun createDivConfiguration(
    context: Context,
    onBackClicked: () -> Unit,
    onIsAppLikedChanged: (Boolean) -> Unit
): DivConfiguration {
    val customActionHandler = DivActionHandler(
        onBackClick = onBackClicked,
        changeLike = onIsAppLikedChanged
    )
    return DivConfiguration.Builder(GlideDivImageLoader(context))
        .actionHandler(customActionHandler)
        .visualErrorsEnabled(true)
        .build()
}

fun JSONObject.asDiv2DataWithTemplates(): DivData {
    val templates = getJSONObject("templates")
    val card = getJSONObject("card")
    val environment = DivParsingEnvironment(ParsingErrorLogger.LOG)
    environment.parseTemplates(templates)
    return DivData(environment, card)
}