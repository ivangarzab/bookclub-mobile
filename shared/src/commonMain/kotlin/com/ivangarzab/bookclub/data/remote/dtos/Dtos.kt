package com.ivangarzab.bookclub.data.remote.dtos

import kotlinx.serialization.Serializable

// ========================================
// CORE REUSABLE DTOs
// ========================================

@Serializable
data class BookDto(
    val id: Int? = null,
    val title: String,
    val author: String,
    val edition: String? = null,
    val year: Int? = null,
    val isbn: String? = null
)

@Serializable
data class DiscussionDto(
    val id: String,
    val session_id: String? = null,
    val title: String,
    val date: String,
    val location: String? = null
)

@Serializable
data class MemberDto(
    val id: String,
    val name: String,
    val points: Int = 0,
    val books_read: Int = 0,
    val user_id: String? = null,
    val role: String? = null,
    val clubs: List<String> = emptyList() // Club IDs
)

@Serializable
data class ClubDto(
    val id: String,
    val name: String,
    val discord_channel: String? = null,
    val server_id: String? = null
)

@Serializable
data class SessionDto(
    val id: String,
    val club_id: String? = null,
    val book: BookDto? = null,
    val due_date: String? = null,
    val discussions: List<DiscussionDto> = emptyList()
)

@Serializable
data class ServerDto(
    val id: String,
    val name: String
)

// ========================================
// COMMON RESPONSE PATTERNS
// ========================================

@Serializable
data class ErrorResponseDto(
    val error: String,
    val partial_success: Boolean? = null,
    val message: String? = null
)

@Serializable
data class DeleteResponseDto(
    val success: Boolean,
    val message: String,
    val warning: String? = null
)

// ========================================
// CLUB SPECIFIC RESPONSES
// ========================================

@Serializable
data class ClubResponseDto(
    val id: String,
    val name: String,
    val discord_channel: String?,
    val server_id: String,
    val members: List<MemberDto>,
    val active_session: SessionDto?,
    val past_sessions: List<SessionDto>,
    val shame_list: List<String> // Member IDs
)

@Serializable
data class CreateClubRequestDto(
    val id: String? = null,
    val name: String,
    val discord_channel: String? = null,
    val server_id: String,
    val members: List<MemberDto>? = null,
    val active_session: SessionDto? = null,
    val shame_list: List<String>? = null
)

@Serializable
data class UpdateClubRequestDto(
    val id: String,
    val server_id: String,
    val name: String? = null,
    val discord_channel: String? = null,
    val shame_list: List<String>? = null
)

@Serializable
data class ClubSuccessResponseDto(
    val success: Boolean,
    val message: String,
    val club: ClubDto,
    val club_updated: Boolean? = null,
    val shame_list_updated: Boolean? = null
)

// ========================================
// MEMBER SPECIFIC RESPONSES
// ========================================

@Serializable
data class MemberResponseDto(
    val id: String,
    val name: String,
    val points: Int,
    val books_read: Int,
    val user_id: String?,
    val role: String?,
    val clubs: List<ClubDto>,
    val shame_clubs: List<ClubDto>
)

@Serializable
data class CreateMemberRequestDto(
    val id: String? = null,
    val name: String,
    val points: Int = 0,
    val books_read: Int = 0,
    val user_id: String? = null,
    val role: String? = null,
    val clubs: List<String>? = null // Club IDs
)

@Serializable
data class UpdateMemberRequestDto(
    val id: String,
    val name: String? = null,
    val points: Int? = null,
    val books_read: Int? = null,
    val user_id: String? = null,
    val role: String? = null,
    val clubs: List<String>? = null
)

@Serializable
data class MemberSuccessResponseDto(
    val success: Boolean,
    val message: String,
    val member: MemberDto,
    val clubs_updated: Boolean? = null
)

// ========================================
// SESSION SPECIFIC RESPONSES
// ========================================

@Serializable
data class SessionResponseDto(
    val id: String,
    val club: ClubDto,
    val book: BookDto,
    val due_date: String?,
    val discussions: List<DiscussionDto>,
    val shame_list: List<MemberDto> // Full member objects for shame list
)

@Serializable
data class CreateSessionRequestDto(
    val id: String? = null,
    val club_id: String,
    val book: BookDto,
    val due_date: String? = null,
    val discussions: List<DiscussionDto>? = null
)

@Serializable
data class UpdateSessionRequestDto(
    val id: String,
    val club_id: String? = null,
    val book: BookDto? = null,
    val due_date: String? = null,
    val discussions: List<DiscussionDto>? = null,
    val discussion_ids_to_delete: List<String>? = null
)

@Serializable
data class SessionSuccessResponseDto(
    val success: Boolean,
    val message: String,
    val session: SessionDto? = null,
    val updates: SessionUpdatesDto? = null
)

@Serializable
data class SessionUpdatesDto(
    val book: Boolean,
    val session: Boolean,
    val discussions: Boolean
)

// ========================================
// SERVER SPECIFIC RESPONSES
// ========================================

@Serializable
data class ServerResponseDto(
    val id: String,
    val name: String,
    val clubs: List<ServerClubDto>
)

@Serializable
data class ServersResponseDto(
    val servers: List<ServerResponseDto>
)

@Serializable
data class ServerClubDto(
    val id: String,
    val name: String,
    val discord_channel: String?,
    val member_count: Int? = null,
    val latest_session: SessionDto? = null
)

@Serializable
data class CreateServerRequestDto(
    val id: String? = null,
    val name: String
)

@Serializable
data class UpdateServerRequestDto(
    val id: String,
    val name: String? = null
)

@Serializable
data class ServerSuccessResponseDto(
    val success: Boolean,
    val message: String,
    val server: ServerDto
)
