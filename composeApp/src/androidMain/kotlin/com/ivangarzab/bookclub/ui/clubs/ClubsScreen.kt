package com.ivangarzab.bookclub.ui.clubs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ivangarzab.bookclub.R
import com.ivangarzab.bookclub.presentation.viewmodels.club.ClubDetailsState
import com.ivangarzab.bookclub.presentation.viewmodels.club.ClubDetailsViewModel
import com.ivangarzab.bookclub.theme.KluvsTheme
import com.ivangarzab.bookclub.ui.components.ErrorScreen
import com.ivangarzab.bookclub.ui.components.LoadingScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ClubsScreen(
    modifier: Modifier = Modifier,
    clubId: String,
    viewModel: ClubDetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(clubId) {
        viewModel.loadClubData(clubId)
    }

    ClubsScreenContent(
        modifier = modifier,
        state = state,
        onRetry = viewModel::refresh
    )
}

@Composable
fun ClubsScreenContent(
    modifier: Modifier = Modifier,
    state: ClubDetailsState,
    onRetry: () -> Unit,
) {
    when {
        state.isLoading -> LoadingScreen()
        state.error != null -> ErrorScreen(
            message = state.error!!,
            onRetry = onRetry
        )
        else -> {
            var selectedTabIndex by remember { mutableIntStateOf(0) }
            val tabs = listOf(
                stringResource(R.string.general),
                stringResource(R.string.active_session),
                stringResource(R.string.members)
            )

            Column(modifier = modifier) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }

                val tabModifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surface)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                // Tab content
                when (selectedTabIndex) {
                    0 -> GeneralTab(tabModifier, state.clubDetails)
                    1 -> ActiveSessionTab(tabModifier, state.activeSession)
                    2 -> MembersTab(tabModifier, state.members)
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_ClubsScreen() = KluvsTheme {
    ClubsScreenContent(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
        state = ClubDetailsState(
            isLoading = false
        ),
        onRetry = { }
    )
}