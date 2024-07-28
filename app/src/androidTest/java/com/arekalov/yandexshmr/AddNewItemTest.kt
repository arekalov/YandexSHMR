package com.arekalov.yandexshmr

import androidx.activity.compose.setContent
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.arekalov.yandexshmr.presentation.MainActivity
import com.arekalov.yandexshmr.presentation.aboutapp.AboutAppViewModel
import com.arekalov.yandexshmr.presentation.common.navigation.Navigation
import com.arekalov.yandexshmr.presentation.edit.EditViewModel
import com.arekalov.yandexshmr.presentation.home.HomeViewModel
import com.arekalov.yandexshmr.presentation.settings.SettingsViewModel
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import java.util.UUID

@HiltAndroidTest
class AddNewItemTest {

    private val uuid = UUID.randomUUID()

    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addNewItemTest() {
        composeTestRule.activity.setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val homeViewModel: HomeViewModel = viewModel()
            val editViewModel: EditViewModel = viewModel()
            val aboutAppViewModel: AboutAppViewModel = viewModel()
            ToDoListTheme {
                val navController = rememberNavController()
                Navigation(
                    navController = navController,
                    settingsViewModel = settingsViewModel,
                    editViewModel = editViewModel,
                    homeViewModel = homeViewModel,
                    aboutAppViewModel = aboutAppViewModel
                )
            }
        }

        val textPlus = composeTestRule.activity.getText(R.string.addItemDescr).toString()
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodes(hasContentDescription(textPlus)).fetchSemanticsNodes()
                .isNotEmpty()
        }

        val btnPlus = composeTestRule.onNodeWithContentDescription(textPlus)
        btnPlus.assertExists().performClick()

        composeTestRule.waitUntil(timeoutMillis = 2000) {
            composeTestRule.onAllNodes(hasSetTextAction()).fetchSemanticsNodes().isNotEmpty()
        }

        val priorityPickerText = composeTestRule.activity.getText(R.string.priorityLabel).toString()
        val priorityPicker = composeTestRule.onNodeWithText(priorityPickerText)
        priorityPicker.assertExists().performClick()

        val priorityHighText = composeTestRule.activity.getString(R.string.highPriorityLabel)
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodes(hasText(priorityHighText)).fetchSemanticsNodes().isNotEmpty()
        }
        val priorityRegular = composeTestRule.onNodeWithText(priorityHighText)
        priorityRegular.assertExists().performClick()

        val textField = composeTestRule.onNode(hasSetTextAction()).assertExists()
        textField.performTextInput(uuid.toString())

        val buttonText = composeTestRule.activity.getText(R.string.saveLabel)
        val saveButton = composeTestRule.onNode(hasText(buttonText.toString()))
        saveButton.assertExists().performClick()


        val mainScreenText = composeTestRule.activity.getString(R.string.myItemsLabel)
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodes(hasText(mainScreenText)).fetchSemanticsNodes().isNotEmpty()
        }

        val item = composeTestRule.onNode(hasText(uuid.toString()))

        val checkBox = item.onChildren()
            .filterToOne(
                hasStateDescription(
                    composeTestRule.activity.getText(R.string.notDoneDescr).toString()
                )
            )
        checkBox.assertExists()
    }

}
