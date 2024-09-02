package com.arekalov.yandexshmr.presentation.home.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.data.repository.FakeHardCodeToDoItemRepository
import com.arekalov.yandexshmr.domain.model.ToDoItemModel
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme

@Composable
fun ItemsList(
    toDoItemModels: List<ToDoItemModel>,
    onCheckedChange: (String, Boolean) -> Unit,
    onClickItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .padding(5.dp)
            .shadow(3.dp, shape = RoundedCornerShape(5))
    ) {
        LazyColumn(
            modifier = Modifier
                .semantics {
                    collectionInfo = CollectionInfo(toDoItemModels.size, 1)
                }
        ) {
            items(
                items = toDoItemModels,
                key = { it.id }
            ) { toDoItem ->
                Item(
                    item = toDoItem,
                    onCheckedChange = { checked -> onCheckedChange(toDoItem.id, checked) },
                    onClickEdit = onClickItem,
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ItemListPreview() {
    ToDoListTheme {
        ItemsList(
            toDoItemModels = FakeHardCodeToDoItemRepository().itemsList,
            onCheckedChange = { _, _ -> },
            onClickItem = {}
        )
    }
}