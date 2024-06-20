package com.arekalov.yandexshmr

import ToDoListTheme
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arekalov.yandexshmr.models.ToDoItemRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                HomeScreen()
            }
        }
    }
}

val repo = ToDoItemRepository().todoItems
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
}
