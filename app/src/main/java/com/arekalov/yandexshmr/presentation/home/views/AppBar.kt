package com.arekalov.yandexshmr.presentation.home.views

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arekalov.yandexshmr.R
import com.arekalov.yandexshmr.data.repository.FakeHardCodeToDoItemRepository
import com.arekalov.yandexshmr.presentation.theme.ToDoListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onVisibleClick: () -> Unit,
    onSettingsClick: () -> Unit,
    isVisibleAll: Boolean,
    doneCount: Int,
    modifier: Modifier = Modifier
) {
    val showAllDescr = stringResource(id = R.string.showAllDescr)
    val showNotDone = stringResource(R.string.showNotDoneDescr)
    val elevation = animateDpAsState(
        targetValue = if (scrollBehavior.state.collapsedFraction > 0.5) {
            20.dp
        } else {
            0.dp
        },
        label = "elevation",
        animationSpec = tween(durationMillis = 400)
    )
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
            .semantics {
                isTraversalGroup = true
            }
            .fillMaxWidth()
            .padding(bottom = 0.dp)
            .shadow(elevation.value)

    ) {
        LargeTopAppBar(
            actions = {
                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp)
                        .semantics {
                            traversalIndex = 0f
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.settingsDescr),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                containerColor = MaterialTheme.colorScheme.background
            ),
            title = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics(
                            mergeDescendants = true
                        ) {
                            traversalIndex = 1f
                            heading()
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 25.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        if (scrollBehavior.state.collapsedFraction >= 0.5) {
                            Text(
                                text = stringResource(R.string.myItemsLabel),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier
                                    .padding(start = 26.dp),
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        } else {
                            Column {
                                Text(
                                    text = stringResource(id = R.string.myItemsLabel),
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(start = 26.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.doneLabel, doneCount),
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.3f),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(start = 26.dp)
                                )
                            }
                        }
                    }
                    IconButton(
                        onClick = onVisibleClick,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 15.dp)
                            .semantics {
                                traversalIndex = 2f
                                stateDescription = if (isVisibleAll) showNotDone else showAllDescr
                            }
                    ) {
                        Icon(
                            painter = painterResource(
                                id =
                                if (isVisibleAll) {
                                    R.drawable.ic_invisible
                                } else {
                                    R.drawable.ic_visibile
                                }
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            scrollBehavior = scrollBehavior
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AppBarPreviewUncollapsed() {
    ToDoListTheme {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState()
        )
        AppBar(
            onVisibleClick = {},
            isVisibleAll = true,
            scrollBehavior = scrollBehavior,
            doneCount = FakeHardCodeToDoItemRepository().itemsList.count { it.isDone },
            onSettingsClick = {}
        )
    }
}