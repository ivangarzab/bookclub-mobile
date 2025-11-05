package com.ivangarzab.bookclub.data.remote.source

import com.ivangarzab.bookclub.data.remote.api.MemberService
import com.ivangarzab.bookclub.data.remote.dtos.CreateMemberRequestDto
import com.ivangarzab.bookclub.data.remote.dtos.UpdateMemberRequestDto
import com.ivangarzab.bookclub.data.remote.mappers.toDomain
import com.ivangarzab.bookclub.domain.models.Member

/**
 * Remote data source for Member operations.
 *
 * Responsibilities:
 * - Calls [MemberService] to fetch/mutate member data from Supabase
 * - Maps DTOs to domain models using mappers
 * - Wraps results in [Result] for error handling
 */
interface MemberRemoteDataSource {

    /**
     * Fetches a member by ID.
     *
     * Returns a [Member] with all nested relations populated:
     * - clubs (full Club objects the member belongs to)
     * - shameClubs (full Club objects where member is shamed)
     */
    suspend fun getMember(memberId: String): Result<Member>

    /**
     * Fetches a member by user ID (auth ID).
     *
     * Returns a [Member] with all nested relations populated.
     */
    suspend fun getMemberByUserId(userId: String): Result<Member>

    /**
     * Creates a new member.
     *
     * Returns the created [Member] (basic info only, no nested relations).
     */
    suspend fun createMember(request: CreateMemberRequestDto): Result<Member>

    /**
     * Updates an existing member.
     *
     * Returns the updated [Member] (basic info only, no nested relations).
     */
    suspend fun updateMember(request: UpdateMemberRequestDto): Result<Member>

    /**
     * Deletes a member by ID.
     *
     * Returns success message on successful deletion.
     */
    suspend fun deleteMember(memberId: String): Result<String>
}

class MemberRemoteDataSourceImpl(
    private val memberService: MemberService
) : MemberRemoteDataSource {

    override suspend fun getMember(memberId: String): Result<Member> {
        return try {
            val dto = memberService.get(memberId)
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMemberByUserId(userId: String): Result<Member> {
        return try {
            val dto = memberService.getByUserId(userId)
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createMember(request: CreateMemberRequestDto): Result<Member> {
        return try {
            val response = memberService.create(request)
            Result.success(response.member.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateMember(request: UpdateMemberRequestDto): Result<Member> {
        return try {
            val response = memberService.update(request)
            Result.success(response.member.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteMember(memberId: String): Result<String> {
        return try {
            val response = memberService.delete(memberId)
            if (response.success) {
                Result.success(response.message)
            } else {
                Result.failure(Exception("Delete failed: ${response.message}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
