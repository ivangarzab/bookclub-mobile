package com.ivangarzab.bookclub.ui.clubs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ivangarzab.bookclub.R
import com.ivangarzab.bookclub.presentation.models.MemberListItemInfo
import com.ivangarzab.bookclub.theme.KluvsTheme
import com.ivangarzab.bookclub.ui.components.NoTabData

@Composable
fun MembersTab(
    modifier: Modifier = Modifier,
    members: List<MemberListItemInfo>
) {
    if (members.isEmpty()) {
        NoTabData(
            modifier = modifier,
            text = R.string.no_members_in_club
        )
        return
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.members_x, members.size),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            LazyColumn {
                itemsIndexed(members) { index, member ->
                    MemberListItem(member.name, member.points)
                    if (index < members.size - 1) {
                        MemberDivider()
                    }
                }
            }
        }
    }
}

@Composable
private fun MemberListItem(
    name: String,
    points: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            )
            Text(
                text = name,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Text(
            text = stringResource(R.string.x_points, points),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun MemberDivider() {
    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
}

@PreviewLightDark
@Composable
fun Preview_MembersTab() = KluvsTheme {
    MembersTab(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        members = listOf(
            MemberListItemInfo("0", "Iván Garza Bermea", 166, ""),
            MemberListItemInfo("1", "Monica Michelle Morales", 100, ""),
            MemberListItemInfo("2", "Marco \"Chitho\" Rivera", 143, ""),
            MemberListItemInfo("3", "Anacleto \"Keto\" Longoria", 42, ""),
            MemberListItemInfo("4", "Joel Oscar Julian Salinas", 0, ""),
            MemberListItemInfo("5", "Ginseng Joaquin Guzman", 69, ""),
        )
    )
}