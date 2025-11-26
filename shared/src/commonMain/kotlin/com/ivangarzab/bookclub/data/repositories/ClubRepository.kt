package com.ivangarzab.bookclub.data.repositories

import com.ivangarzab.bookclub.data.remote.dtos.CreateClubRequestDto
import com.ivangarzab.bookclub.data.remote.dtos.UpdateClubRequestDto
import com.ivangarzab.bookclub.data.remote.source.ClubRemoteDataSource
import com.ivangarzab.bookclub.domain.models.Club

/**
 * Repository for managing Club data.
 *
 * This repository abstracts the data source layer and provides a clean API for accessing
 * club-related data. Currently delegates to remote data source, but can be extended
 * to support local caching and offline capabilities.
 */
interface ClubRepository {

    /**
     * Retrieves a single club by its ID and server ID.
     *
     * @param clubId The ID of the club to retrieve
     * @param serverId The ID of the server the club belongs to
     * @return Result containing the Club (with nested members, sessions, etc.) if successful,
     *         or an error if the operation failed
     */
    suspend fun getClub(clubId: String, serverId: String): Result<Club>

    /**
     * Creates a new club.
     *
     * @param name The name of the club
     * @param serverId The ID of the server this club belongs to
     * @param discordChannel Optional Discord channel to associate with this club
     * @return Result containing the created Club if successful, or an error if the operation failed
     */
    suspend fun createClub(
        name: String,
        serverId: String,
        discordChannel: String?
    ): Result<Club>

    /**
     * Updates an existing club.
     *
     * @param clubId The ID of the club to update
     * @param serverId The ID of the server the club belongs to
     * @param name Optional new name for the club (null to keep current value)
     * @param discordChannel Optional new Discord channel (null to keep current value)
     * @return Result containing the updated Club if successful, or an error if the operation failed
     */
    suspend fun updateClub(
        clubId: String,
        serverId: String,
        name: String?,
        discordChannel: String?
    ): Result<Club>

    /**
     * Deletes a club by its ID.
     *
     * @param clubId The ID of the club to delete
     * @param serverId The ID of the server the club belongs to
     * @return Result containing success message if deletion was successful, or an error if the operation failed
     */
    suspend fun deleteClub(clubId: String, serverId: String): Result<String>
}

/**
 * Implementation of [ClubRepository] that delegates to remote data sources.
 *
 * This is a simple pass-through implementation that can be extended later to include
 * caching strategies, offline support, and data synchronization.
 *
 * Note: The API returns nested data (members, sessions, etc.) with Club responses.
 * Future implementations may decompose this nested data and coordinate with other
 * repositories for caching purposes.
 */
internal class ClubRepositoryImpl(
    private val clubRemoteDataSource: ClubRemoteDataSource
) : ClubRepository {

    override suspend fun getClub(clubId: String, serverId: String): Result<Club> =
        clubRemoteDataSource.getClub(clubId, serverId)

    override suspend fun createClub(
        name: String,
        serverId: String,
        discordChannel: String?
    ): Result<Club> =
        clubRemoteDataSource.createClub(
            CreateClubRequestDto(
                name = name,
                server_id = serverId,
                discord_channel = discordChannel
            )
        )

    override suspend fun updateClub(
        clubId: String,
        serverId: String,
        name: String?,
        discordChannel: String?
    ): Result<Club> =
        clubRemoteDataSource.updateClub(
            UpdateClubRequestDto(
                id = clubId,
                server_id = serverId,
                name = name,
                discord_channel = discordChannel
            )
        )

    override suspend fun deleteClub(clubId: String, serverId: String): Result<String> =
        clubRemoteDataSource.deleteClub(clubId, serverId)
}
