package com.ivangarzab.bookclub.data.remote.source

import com.ivangarzab.bookclub.data.remote.api.ClubService
import com.ivangarzab.bookclub.data.remote.dtos.CreateClubRequestDto
import com.ivangarzab.bookclub.data.remote.dtos.UpdateClubRequestDto
import com.ivangarzab.bookclub.data.remote.mappers.toDomain
import com.ivangarzab.bookclub.domain.models.Club

/**
 * Remote data source for Club operations.
 *
 * Responsibilities:
 * - Calls [ClubService] to fetch/mutate club data from Supabase
 * - Maps DTOs to domain models using mappers
 * - Wraps results in [Result] for error handling
 */
interface ClubRemoteDataSource {

    /**
     * Fetches a club by ID and server ID.
     *
     * Returns a [Club] with all nested relations populated:
     * - members (full Member objects)
     * - activeSession (full Session object)
     * - pastSessions (full Session objects)
     * - shameList (member IDs)
     */
    suspend fun getClub(clubId: String, serverId: String): Result<Club>

    /**
     * Fetches a club by Discord channel ID and server ID.
     *
     * Returns a [Club] with all nested relations populated.
     */
    suspend fun getClubByChannel(channel: String, serverId: String): Result<Club>

    /**
     * Creates a new club.
     *
     * Returns the created [Club] (basic info only, no nested relations).
     */
    suspend fun createClub(request: CreateClubRequestDto): Result<Club>

    /**
     * Updates an existing club.
     *
     * Returns the updated [Club] (basic info only, no nested relations).
     */
    suspend fun updateClub(request: UpdateClubRequestDto): Result<Club>

    /**
     * Deletes a club by ID and server ID.
     *
     * Returns success message on successful deletion.
     */
    suspend fun deleteClub(clubId: String, serverId: String): Result<String>
}

class ClubRemoteDataSourceImpl(
    private val clubService: ClubService
) : ClubRemoteDataSource {

    override suspend fun getClub(clubId: String, serverId: String): Result<Club> {
        return try {
            val dto = clubService.get(clubId, serverId)
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getClubByChannel(channel: String, serverId: String): Result<Club> {
        return try {
            val dto = clubService.getByChannel(channel, serverId)
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createClub(request: CreateClubRequestDto): Result<Club> {
        return try {
            val response = clubService.create(request)
            Result.success(response.club.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateClub(request: UpdateClubRequestDto): Result<Club> {
        return try {
            val response = clubService.update(request)
            Result.success(response.club.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteClub(clubId: String, serverId: String): Result<String> {
        return try {
            val response = clubService.delete(clubId, serverId)
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
