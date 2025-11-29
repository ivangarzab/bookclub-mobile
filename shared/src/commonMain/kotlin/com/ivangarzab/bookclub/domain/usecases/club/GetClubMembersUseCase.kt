package com.ivangarzab.bookclub.domain.usecases.club

import com.ivangarzab.bookclub.data.repositories.ClubRepository
import com.ivangarzab.bookclub.domain.models.Club
import com.ivangarzab.bookclub.domain.models.Member
import com.ivangarzab.bookclub.presentation.models.MemberListItem

/**
 * UseCase for fetching club members sorted by points for MembersTab.
 *
 * Transforms domain [Member] models into UI-friendly [MemberListItem] with:
 * - Member information
 * - Points for ranking
 * - Sorted by points (descending)
 *
 * @param clubRepository Repository for club data
 */
class GetClubMembersUseCase(
    private val clubRepository: ClubRepository
) {
    /**
     * Fetches club members sorted by points.
     *
     * Members are returned in descending order by points (highest first).
     * Returns empty list if club has no members.
     *
     * @param clubId The ID of the club to retrieve members for
     * @return Result containing list of [MemberListItem] if successful, or error if failed
     */
    suspend operator fun invoke(clubId: String): Result<List<MemberListItem>> {
        return clubRepository.getClub(clubId).map { club: Club ->
            club.members?.map { member: Member ->
                MemberListItem(
                    memberId = member.id,
                    name = member.name,
                    points = member.points,
                    avatarUrl = null // TODO: Add avatar support when available
                )
            }?.sortedByDescending { it.points } ?: emptyList()
        }
    }
}
