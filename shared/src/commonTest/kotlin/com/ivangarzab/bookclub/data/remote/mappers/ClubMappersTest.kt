package com.ivangarzab.bookclub.data.remote.mappers

import com.ivangarzab.bookclub.data.remote.dtos.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ClubMappersTest {

    @Test
    fun `ClubDto toDomain maps basic fields only`() {
        // Given: A ClubDto with basic info
        val dto = ClubDto(
            id = "club-1",
            name = "Book Club",
            discord_channel = "123456789",
            server_id = "987654321"
        )

        // When: Mapping to domain
        val domain = dto.toDomain()

        // Then: Basic fields are mapped, relations are null
        assertEquals("club-1", domain.id)
        assertEquals("Book Club", domain.name)
        assertEquals("123456789", domain.discordChannel)
        assertEquals("987654321", domain.serverId)
        assertTrue(domain.shameList.isEmpty())
        assertNull(domain.members)
        assertNull(domain.activeSession)
        assertNull(domain.pastSessions)
    }

    @Test
    fun `ClubResponseDto toDomain maps all nested relations`() {
        // Given: A ClubResponseDto with nested data
        val memberDto = MemberDto(
            id = "1",
            name = "John Doe",
            points = 100,
            books_read = 5,
            user_id = "user-1",
            role = "admin",
            clubs = emptyList()
        )

        val bookDto = BookDto(
            id = "book-1",
            title = "Test Book",
            author = "Test Author"
        )

        val sessionDto = SessionDto(
            id = "session-1",
            club_id = "club-1",
            book = bookDto,
            due_date = "2024-12-31T00:00:00",
            discussions = emptyList()
        )

        val dto = ClubResponseDto(
            id = "club-1",
            name = "Full Club",
            discord_channel = "123456789",
            server_id = "987654321",
            members = listOf(memberDto),
            active_session = sessionDto,
            past_sessions = listOf(sessionDto),
            shame_list = listOf("1", "2")
        )

        // When: Mapping to domain
        val domain = dto.toDomain()

        // Then: All nested relations are mapped
        assertEquals("club-1", domain.id)
        assertEquals("Full Club", domain.name)
        assertNotNull(domain.members)
        assertEquals(1, domain.members?.size)
        assertEquals("John Doe", domain.members?.first()?.name)
        assertNotNull(domain.activeSession)
        assertEquals("session-1", domain.activeSession?.id)
        assertNotNull(domain.pastSessions)
        assertEquals(1, domain.pastSessions?.size)
        assertEquals(2, domain.shameList.size)
        assertTrue(domain.shameList.contains("1"))
        assertTrue(domain.shameList.contains("2"))
    }

    @Test
    fun `ServerClubDto toDomain maps with latest session`() {
        // Given: A ServerClubDto
        val sessionDto = SessionDto(
            id = "session-1",
            club_id = "club-1",
            book = BookDto(id = "book-1", title = "Book", author = "Author"),
            due_date = null,
            discussions = emptyList()
        )

        val dto = ServerClubDto(
            id = "club-1",
            name = "Server Club",
            discord_channel = "123456789",
            member_count = 10,
            latest_session = sessionDto
        )

        // When: Mapping to domain
        val domain = dto.toDomain()

        // Then: Basic fields and latest session are mapped
        assertEquals("club-1", domain.id)
        assertEquals("Server Club", domain.name)
        assertEquals("123456789", domain.discordChannel)
        assertNull(domain.serverId) // Not available in ServerClubDto
        assertNotNull(domain.activeSession)
        assertEquals("session-1", domain.activeSession?.id)
        assertNull(domain.members)
        assertNull(domain.pastSessions)
        assertTrue(domain.shameList.isEmpty())
    }

    @Test
    fun `ServerClubDto toDomain handles null latest session`() {
        // Given: A ServerClubDto without latest session
        val dto = ServerClubDto(
            id = "club-2",
            name = "Empty Club",
            discord_channel = null,
            member_count = 0,
            latest_session = null
        )

        // When: Mapping to domain
        val domain = dto.toDomain()

        // Then: No active session
        assertEquals("club-2", domain.id)
        assertNull(domain.activeSession)
    }
}
