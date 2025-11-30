package com.ivangarzab.bookclub.data.remote.mappers

import com.ivangarzab.bookclub.data.remote.dtos.SessionDto
import com.ivangarzab.bookclub.data.remote.dtos.SessionResponseDto
import com.ivangarzab.bookclub.domain.models.Session
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime

/**
 * Maps a [SessionDto] from the API to a [Session] domain model.
 *
 * Note: SessionDto may have partial data (used in nested contexts).
 * For full session data, use [SessionResponseDto.toDomain].
 */
fun SessionDto.toDomain(): Session {
    return Session(
        id = id,
        clubId = club_id ?: "",
        book = book?.toDomain() ?: error("SessionDto missing required book data"),
        dueDate = due_date?.let { parseDateString(it) },
        discussions = discussions.map { it.toDomain() }
    )
}

/**
 * Maps a [SessionResponseDto] from the API to a [Session] domain model.
 *
 * This is the full session response with all related data populated.
 */
fun SessionResponseDto.toDomain(): Session {
    return Session(
        id = id,
        clubId = club.id,
        book = book.toDomain(),
        dueDate = due_date?.let { parseDateString(it) },
        discussions = discussions.map { it.toDomain() }
    )
}

/**
 * Helper function to parse a date string that could be either:
 * - Date-only format: "2024-12-31"
 * - DateTime format: "2024-12-31T23:59:59"
 */
private fun parseDateString(dateString: String): LocalDateTime {
    return try {
        // Try parsing as full DateTime first
        LocalDateTime.parse(dateString)
    } catch (e: Exception) {
        // If that fails, parse as date-only and add midnight time
        LocalDate.parse(dateString).atTime(0, 0)
    }
}
