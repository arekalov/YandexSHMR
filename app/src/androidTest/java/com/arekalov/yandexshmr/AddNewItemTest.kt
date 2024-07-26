import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
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

@HiltAndroidTest
class AddNewItemTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAddNewItem() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val editViewModel: EditViewModel = hiltViewModel()
            val homeViewModel: HomeViewModel = hiltViewModel()
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val aboutAppViewModel: AboutAppViewModel = hiltViewModel()
            val theme = settingsViewModel.settingsState.collectAsState()
            ToDoListTheme(theme = theme.value.theme) {
                Navigation(
                    navController = navController,
                    editViewModel = editViewModel,
                    homeViewModel = homeViewModel,
                    settingsViewModel = settingsViewModel,
                    aboutAppViewModel = aboutAppViewModel
                )
            }
        }

        // Add your UI test logic here
    }
}
