package com.arekalov.yandexshmr.presentation.aboutapp.views

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.arekalov.yandexshmr.presentation.aboutapp.models.AboutAppViewState
import com.arekalov.yandexshmr.presentation.common.models.AppTheme
import com.yandex.div.DivDataTag
import com.yandex.div.core.view2.Div2View
import com.yandex.div.data.DivParsingEnvironment
import com.yandex.div.json.ParsingErrorLogger
import com.yandex.div2.DivData
import org.json.JSONObject

@Composable
fun AboutAppScreenDisplay(
    viewState: AboutAppViewState,
    div2View: Div2View,
    modifier: Modifier = Modifier,
) {
    val theme = if (viewState.theme == AppTheme.LIGHT) {
        "light"
    } else if (viewState.theme == AppTheme.DARK) {
        "dark"
    } else {
        if (isSystemInDarkTheme()) "dark" else "light"
    }
    val divData = JSONObject(getJson(theme, viewState.isAppLiked)).asDiv2DataWithTemplates()
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { ctx ->
            div2View.apply {
                setData(divData, DivDataTag("your_unique_tag_here"))
            }
        }
    )
}

fun JSONObject.asDiv2DataWithTemplates(): DivData {
    val templates = getJSONObject("templates")
    val card = getJSONObject("card")
    val environment = DivParsingEnvironment(ParsingErrorLogger.LOG)
    environment.parseTemplates(templates)
    return DivData(environment, card)
}