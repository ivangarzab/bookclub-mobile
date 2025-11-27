package com.ivangarzab.bookclub.ui.me

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ivangarzab.bookclub.R
import com.ivangarzab.bookclub.theme.KluvsTheme

@Composable
fun StatisticsSection(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.your_statistics),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.padding(8.dp))

            StatisticsItem(
                label = stringResource(R.string.no_of_clubs),
                value = "XX"
            )

            Spacer(Modifier.padding(4.dp))

            StatisticsItem(
                label = stringResource(R.string.points),
                value = "YYY"
            )

            Spacer(Modifier.padding(4.dp))

            StatisticsItem(
                label = stringResource(R.string.books_read),
                value = "ZZ"
            )
        }
    }
}

@Composable
private fun StatisticsItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(42.dp),
            imageVector = Icons.Default.AccountBox,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.padding(4.dp))
        Column {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview_StatisticsItem() = KluvsTheme {
    StatisticsItem(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
        label = "Section",
        value = "100"
    )
}

@PreviewLightDark
@Composable
private fun Preview_StatisticsSection() = KluvsTheme {
    StatisticsSection(modifier = Modifier.background(color = MaterialTheme.colorScheme.background))
}