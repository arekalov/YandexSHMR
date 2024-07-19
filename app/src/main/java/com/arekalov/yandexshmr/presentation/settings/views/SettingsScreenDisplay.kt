package com.arekalov.yandexshmr.presentation.settings.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.presentation.common.models.AppTheme
import com.arekalov.yandexshmr.presentation.settings.models.SettingsViewState
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenDisplay(
    onBackClicked: () -> Unit,
    viewState: SettingsViewState,
    modifier: Modifier = Modifier,
    onThemeChanged: (AppTheme) -> Unit,
    onAboutAppClick: () -> Unit

) {
    val themeBottomSheetState = rememberModalBottomSheetState()
    var isThemeBottomSheetShowed by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            SettingsTopBar(
                onBackClicked = onBackClicked
            )
        }
    ) { paddingValues: PaddingValues ->
        if (isThemeBottomSheetShowed) {
            ThemeBottomSheet(
                sheetState = themeBottomSheetState,
                onDismissRequest = { isThemeBottomSheetShowed = false },
                changeTheme = onThemeChanged,
                nowTHeme = viewState.theme
            )
        }
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                .shadow(3.dp, shape = RoundedCornerShape(5)),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column {
                SetThemeItem(
                    onClick = { isThemeBottomSheetShowed = true },
                    nowTheme = viewState.theme
                )
                AboutAppItem(
                    onClick = onAboutAppClick
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun SettingsScreenDisplayPreview() {
    ToDoListTheme {
        SettingsScreenDisplay(
            onBackClicked = {},
            onThemeChanged = {},
            onAboutAppClick = {},
            viewState = SettingsViewState(
                theme = AppTheme.DARK,
                isAppLiked = false,
            )
        )
    }
}