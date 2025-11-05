package com.ivangarzab.bookclub.data.remote.mappers

import com.ivangarzab.bookclub.data.remote.dtos.DiscussionDto
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DiscussionMappersTest {

    @Test
    fun `toDomain maps all fields correctly`() {
        // Given: A DiscussionDto with all fields
        val dto = DiscussionDto(
            id = "disc-1",
            session_id = "session-123",
            title = "Chapter 1 Discussion",
            date = "2024-06-15T18:00:00",
            location = "Discord Voice Channel"
        )

        // When: Mapping to domain
        val domain = dto.toDomain()

        // Then: All fields are mapped correctly
        assertEquals("disc-1", domain.id)
        assertEquals("session-123", domain.sessionId)
        assertEquals("Chapter 1 Discussion", domain.title)
        assertEquals(LocalDateTime(2024, 6, 15, 18, 0, 0), domain.date)
        assertEquals("Discord Voice Channel", domain.location)
    }

    @Test
    fun `toDomain handles nullable fields correctly`() {
        // Given: A DiscussionDto with nullable fields null
        val dto = DiscussionDto(
            id = "disc-2",
            session_id = null,
            title = "Final Discussion",
            date = "2024-12-31T20:00:00",
            location = null
        )

        // When: Mapping to domain
        val domain = dto.toDomain()

        // Then: Nullable fields are null
        assertEquals("disc-2", domain.id)
        assertNull(domain.sessionId)
        assertEquals("Final Discussion", domain.title)
        assertEquals(LocalDateTime(2024, 12, 31, 20, 0, 0), domain.date)
        assertNull(domain.location)
    }

    @Test
    fun `toDomain parses ISO 8601 date correctly`() {
        // Given: A DiscussionDto with various date formats
        val dto1 = DiscussionDto(
            id = "disc-3",
            session_id = null,
            title = "Test",
            date = "2024-01-01T00:00:00",
            location = null
        )

        // When: Mapping to domain
        val domain1 = dto1.toDomain()

        // Then: Date is parsed correctly
        assertEquals(LocalDateTime(2024, 1, 1, 0, 0, 0), domain1.date)
    }
}
