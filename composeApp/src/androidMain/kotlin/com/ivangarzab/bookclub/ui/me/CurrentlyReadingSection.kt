package com.ivangarzab.bookclub.ui.me

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ivangarzab.bookclub.R
import com.ivangarzab.bookclub.theme.KluvsTheme

@Composable
fun CurrentlyReadingSection(
    modifier: Modifier = Modifier,
    currentReadings: List<Pair<String, Float>>,
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
                text = stringResource(R.string.currently_reading),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.padding(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(currentReadings) { reading ->
                    CurrentlyReadingItem(title = reading.first, progress = reading.second)
                }
            }
        }
    }
}

@Composable
private fun CurrentlyReadingItem(
    modifier: Modifier = Modifier,
    title: String,
    progress: Float,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(Modifier.padding(4.dp))

        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = { progress },
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            gapSize = 0.dp,
            drawStopIndicator = { }
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview_CurrentlyReadingItem() = KluvsTheme {
    CurrentlyReadingItem(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
        title = "1984",
        progress = 0.69f
    )
}

@PreviewLightDark
@Composable
private fun Preview_CurrentlyReadingSection() = KluvsTheme {
    CurrentlyReadingSection(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
        currentReadings = testCurrentlyReadingData
    )
}

val testCurrentlyReadingData = listOf(
    Pair("The Myth of Sisyphus", 0.5f),
    Pair("Pachita", 0.1f),
)