package com.ivangarzab.bookclub.data.remote.mappers

import com.ivangarzab.bookclub.data.remote.dtos.DiscussionDto
import com.ivangarzab.bookclub.domain.models.Discussion
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime

/**
 * Maps a [DiscussionDto] from the API to a [Discussion] domain model.
 */
fun DiscussionDto.toDomain(): Discussion {
    return Discussion(
        id = id,
        sessionId = session_id,
        title = title,
        date = try {
            // Try parsing as full datetime (e.g., "2025-11-15T19:00:00")
            LocalDateTime.parse(date)
        } catch (e: Exception) {
            // If that fails, parse as date-only and default to 7:00 PM
            LocalDate.parse(date).atTime(19, 0)
        },
        location = location
    )
}

/**
 * Maps a [Discussion] domain model to a [DiscussionDto].
 */
fun Discussion.toDto(): DiscussionDto = DiscussionDto(
    id = id,
    session_id = sessionId,
    title = title,
    date = date.toString(),
    location = location
)
