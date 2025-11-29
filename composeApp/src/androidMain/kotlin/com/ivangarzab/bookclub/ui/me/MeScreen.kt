package com.ivangarzab.bookclub.ui.me

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ivangarzab.bookclub.R
import com.ivangarzab.bookclub.theme.KluvsTheme

@Composable
fun MeScreen(
    modifier: Modifier = Modifier,
    currentReadings: List<Pair<String, Float>>,
) {
    Column(
        modifier = modifier
            .padding(16.dp),
    ) {
        Header(imageUrl = "", name = "Member Name", handle = "@username", joinDate = "")
        Divider()
        StatisticsSection(modifier = Modifier.fillMaxWidth())
        Divider()
        CurrentlyReadingSection(
            modifier = Modifier.fillMaxWidth(),
            currentReadings = currentReadings
        )
        Divider()
        Footer(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun Header(
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
    MeScreen(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
        currentReadings = testCurrentlyReadingData
    )
}