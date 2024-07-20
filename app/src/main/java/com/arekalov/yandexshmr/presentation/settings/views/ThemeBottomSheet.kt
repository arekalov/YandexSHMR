package com.arekalov.yandexshmr.presentation.settings.views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.presentation.common.models.AppTheme
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    changeTheme: (AppTheme) -> Unit,
    nowTHeme: AppTheme,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = stringResource(id = R.string.appThemeLabel),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Surface(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .shadow(3.dp, shape = RoundedCornerShape(5)),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surface)
                ) {
                    ThemeItem(
                        text = stringResource(id = R.string.SystemTheme),
                        changeTheme = { changeTheme(AppTheme.SYSTEM) },
                        isSelected = nowTHeme == AppTheme.SYSTEM
                    )
                    ThemeItem(
                        text = stringResource(id = R.string.DarkTheme),
                        changeTheme = { changeTheme(AppTheme.DARK) },
                        isSelected = nowTHeme == AppTheme.DARK
                    )
                    ThemeItem(
                        text = stringResource(id = R.string.LightTheme),
                        changeTheme = { changeTheme(AppTheme.LIGHT) },
                        isSelected = nowTHeme == AppTheme.LIGHT
                    )
                }
            }
        }
    }
}

@Composable
fun ThemeItem(
    changeTheme: () -> Unit,
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = changeTheme,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
            )
            if (isSelected) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    modifier = Modifier.height(18.dp),
                    contentDescription = stringResource(R.string.selectedDescr)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ThemeBottomSheetPreview() {
    ToDoListTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ThemeItem(
                text = stringResource(id = R.string.SystemTheme),
                changeTheme = { },
                isSelected = true
            )
            ThemeItem(
                text = stringResource(id = R.string.DarkTheme),
                changeTheme = { },
                isSelected = false
            )
            ThemeItem(
                text = stringResource(id = R.string.LightTheme),
                changeTheme = { },
                isSelected = false
            )
        }
    }
}