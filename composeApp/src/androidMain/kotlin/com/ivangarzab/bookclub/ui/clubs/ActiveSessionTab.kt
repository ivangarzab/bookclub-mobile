package com.ivangarzab.bookclub.ui.clubs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.ivangarzab.bookclub.R
import com.ivangarzab.bookclub.theme.KluvsTheme
import com.ivangarzab.bookclub.ui.components.NextDiscussionCard

// Dummy discussion data
data class DummyDiscussion(
    val title: String,
    val date: String,
    val location: String,
    val isPast: Boolean,
    val isNext: Boolean
)
val dummyDiscussions = listOf(
    DummyDiscussion("Discussion #1", "Jan 15, 2025 at 7:00 PM", "Coffee Shop", isPast = true, isNext = false),
    DummyDiscussion("Discussion #2", "Jan 29, 2025 at 7:00 PM", "Library", isPast = true, isNext = false),
    DummyDiscussion("Discussion #3", "Feb 12, 2025 at 7:00 PM", "Book Store", isPast = false, isNext = true),
    DummyDiscussion("Discussion #4", "Feb 26, 2025 at 7:00 PM", "Community Center", isPast = false, isNext = false),
    DummyDiscussion("Discussion #5", "Feb 28, 2025 at 7:00 PM", "Community Center", isPast = false, isNext = false),
    DummyDiscussion("Discussion #6", "March 6, 2025 at 7:00 PM", "Home", isPast = false, isNext = false),
)

@Composable
fun ActiveSessionTab(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Session Book Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.sessions_book),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.by_author, "Author"),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                            append(stringResource(R.string.due_date))
                        }
                        append(" Month Day, Year")
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                text = stringResource(R.string.discussion_timeline),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )

            // Timeline
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                itemsIndexed(dummyDiscussions) { index, discussion ->
                    DiscussionTimelineItem(
                        discussion = discussion,
                        isFirst = index == 0,
                        isLast = index == dummyDiscussions.size - 1
                    )
                }
            }
        }
    }
}

@Composable
private fun DiscussionTimelineItem(
    discussion: DummyDiscussion,
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    val lineColor = MaterialTheme.colorScheme.surfaceVariant

    Row(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.width(32.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            // Vertical line through entire height
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top line section
                if (!isFirst) {
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(40.dp)
                            .background(lineColor)
                    )
                } else {
                    Spacer(Modifier.height(40.dp))
                }

                // Spacer for bottom part
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(60.dp)
                        .background(if (!isLast) lineColor else Color.Transparent)
                )
            }

            // Circle indicator positioned at top with padding
            Box(
                modifier = Modifier
                    .padding(top = 28.dp)
                    .size(24.dp)
                    .background(
                        color = if (discussion.isPast || discussion.isNext) {
                            MaterialTheme.colorScheme.primary.copy(
                                alpha = if (discussion.isPast) 0.75f else 1.0f
                            )
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (discussion.isPast) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(Modifier.width(12.dp))

        // Discussion content
        if (discussion.isNext) {
            // Next discussion with orange box
            NextDiscussionCard(
                modifier = Modifier.fillMaxWidth(),
                title = discussion.title,
                location = discussion.location,
                formattedDate = discussion.date
            )
        } else {
            // Past discussions are grayed out
            val textColor = MaterialTheme.colorScheme.onSurface.copy(
                alpha = if (discussion.isPast) 0.5f else 1.0f
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = discussion.title,
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = textColor
                    )
                    Text(
                        text = discussion.location,
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = discussion.date,
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun Preview_ActiveSessionTab() = KluvsTheme {
    ActiveSessionTab(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}