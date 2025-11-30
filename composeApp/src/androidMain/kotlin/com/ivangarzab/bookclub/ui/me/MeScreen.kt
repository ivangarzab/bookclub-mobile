package com.ivangarzab.bookclub.ui.me

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ivangarzab.bookclub.R
import com.ivangarzab.bookclub.presentation.models.CurrentlyReadingBook
import com.ivangarzab.bookclub.presentation.models.UserProfile
import com.ivangarzab.bookclub.presentation.models.UserStatistics
import com.ivangarzab.bookclub.presentation.viewmodels.member.MeState
import com.ivangarzab.bookclub.presentation.viewmodels.member.MeViewModel
import com.ivangarzab.bookclub.theme.KluvsTheme
import com.ivangarzab.bookclub.ui.components.ErrorScreen
import com.ivangarzab.bookclub.ui.components.LoadingScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MeScreen(
    modifier: Modifier = Modifier,
    userId: String,
    viewModel: MeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadUserData(userId)
    }

    MeScreenContent(
        modifier = modifier,
        state = state,
        onRetry = viewModel::refresh
    )
}

@Composable
fun MeScreenContent(
    modifier: Modifier = Modifier,
    state: MeState,
    onRetry: () -> Unit,
) {
    when {
        state.isLoading -> LoadingScreen()
        state.error != null -> ErrorScreen(
            message = state.error!!,
            onRetry = onRetry
        )
        else -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            ) {
                ProfileSection(
                    imageUrl = state.profile?.avatarUrl ?: "",
                    name = state.profile?.name ?: "",
                    handle = state.profile?.name ?: "",
                    joinDate = state.profile?.joinDate ?: ""
                )
                Divider()
                StatisticsSection(
                    modifier = Modifier.fillMaxWidth(),
                    data = state.statistics
                )
                Divider()
                CurrentlyReadingSection(
                    modifier = Modifier.fillMaxWidth(),
                    currentReadings = state.currentlyReading
                )
                Divider()
                Footer(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun ProfileSection(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    handle: String,
    joinDate: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder avatar
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            )

            Spacer(Modifier.padding(8.dp))

            Column {
                Text(
                    text = name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = handle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(R.string.member_since_x, joinDate),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            FooterItem(label = "Settings")
            Spacer(modifier.padding(vertical = 8.dp))
            Divider(color = MaterialTheme.colorScheme.inverseOnSurface)
            Spacer(modifier.padding(vertical = 8.dp))
            FooterItem(label = "Help & Support")
            Spacer(modifier.padding(vertical = 8.dp))
            Divider(color = MaterialTheme.colorScheme.inverseOnSurface)
            Spacer(modifier.padding(vertical = 4.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = stringResource(R.string.version_x, "0.0.1"),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Composable
private fun FooterItem(
    modifier: Modifier = Modifier,
    label: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.padding(horizontal = 4.dp))
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun Divider(
    color: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    HorizontalDivider(color = color)
}

@PreviewLightDark
@Composable
fun Preview_MeScreen() = KluvsTheme {
    MeScreenContent(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
        state = MeState(
            isLoading = false,
            profile = UserProfile(
                memberId = "0",
                name = "Quill",
                handle = "@quill-bot",
                joinDate = "2025",
                avatarUrl = null
            ),
            statistics = UserStatistics(clubsCount = 6, totalPoints = 100, booksRead = 2),
            currentlyReading = listOf(
                CurrentlyReadingBook(bookTitle = "1984", clubName = "Quill's Club", progress = 0.66f, dueDate = "Tomorrow")
            )
        ),
        onRetry = { }
    )
}