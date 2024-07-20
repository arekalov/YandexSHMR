package com.arekalov.yandexshmr.presentation.edit.views

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.domain.model.Priority
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityBottomSheet(
    nowPriority: Priority,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    changePriority: (Priority) -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        PriorityBottomSheetContent(
            changePriority = changePriority,
            nowPriority = nowPriority
        )
    }
}

@Composable
fun PriorityItem(
    text: String,
    color: Color,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedColor by animateColorAsState(
        if (isSelected) color else color.copy(alpha = 0.4f),
        label = ""
    )

    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = animatedColor,
            textDecoration = if (isSelected) {
                TextDecoration.Underline
            } else {
                null
            }
        )
    }
}

@Composable
fun PriorityBottomSheetContent(
    nowPriority: Priority,
    changePriority: (Priority) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.priorityLabel),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .shadow(8.dp, shape = RoundedCornerShape(10))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            PriorityItem(
                text = stringResource(id = R.string.highPriorityLabel),
                color = MaterialTheme.colorScheme.tertiary,
                onClick = { changePriority(Priority.HIGH) },
                isSelected = nowPriority == Priority.HIGH
            )
            PriorityItem(
                text = stringResource(id = R.string.reqularPriorityLabel),
                color = MaterialTheme.colorScheme.onSurface,
                onClick = { changePriority(Priority.REGULAR) },
                isSelected = nowPriority == Priority.REGULAR
            )
            PriorityItem(
                text = stringResource(id = R.string.lowPriorityLabel),
                color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                onClick = { changePriority(Priority.LOW) },
                isSelected = nowPriority == Priority.LOW
            )
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PriorityItemPreview() {
    ToDoListTheme {
        PriorityBottomSheetContent(
            changePriority = {},
            nowPriority = Priority.HIGH
        )
    }
}