package com.ivangarzab.bookclub.data.remote.mappers

import com.ivangarzab.bookclub.data.remote.dtos.DiscussionDto
import com.ivangarzab.bookclub.domain.models.Discussion
import kotlinx.datetime.LocalDateTime

/**
 * Maps a [DiscussionDto] from the API to a [Discussion] domain model.
 */
fun DiscussionDto.toDomain(): Discussion {
    return Discussion(
        id = id,
        sessionId = session_id,
        title = title,
        date = LocalDateTime.parse(date), // Parse ISO 8601 date string to LocalDateTime
        location = location
    )
}
