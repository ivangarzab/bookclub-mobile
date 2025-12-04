package com.ivangarzab.bookclub.presentation.viewmodels.club

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivangarzab.bark.Bark
import com.ivangarzab.bookclub.domain.usecases.club.GetActiveSessionUseCase
import com.ivangarzab.bookclub.domain.usecases.club.GetClubDetailsUseCase
import com.ivangarzab.bookclub.domain.usecases.club.GetClubMembersUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The purpose of this [ViewModel] class is to serve the Club screen.
 */
class ClubDetailsViewModel(
    private val getClubDetails: GetClubDetailsUseCase,
    private val getActiveSession: GetActiveSessionUseCase,
    private val getClubMembers: GetClubMembersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ClubDetailsState())
    val state: StateFlow<ClubDetailsState> = _state.asStateFlow()

    private var currentClubId: String? = null

    fun loadClubData(clubId: String) {
        currentClubId = clubId

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            // Launch all 3 UseCase calls in parallel
            val deferredDetails = async { getClubDetails(clubId) }
            val deferredSession = async { getActiveSession(clubId) }
            val deferredMembers = async { getClubMembers(clubId) }

            // Await all results
            val detailsResult = deferredDetails.await()
            val sessionResult = deferredSession.await()
            val membersResult = deferredMembers.await()

            // Aggregate errors
            val errors = listOfNotNull(
                detailsResult.exceptionOrNull()?.message,
                sessionResult.exceptionOrNull()?.message,
                membersResult.exceptionOrNull()?.message
            )
            val error = when {
                errors.isEmpty() -> null
                errors.distinct().size == 1 -> errors.first() // All errors are identical
                else -> "Multiple errors occurred"
            }
            error?.let { e ->
                Bark.e("Error fetching club details: $e")
            } ?: Bark.v("Got club details successfully")

            // Update state with all results
            _state.update {
                it.copy(
                    isLoading = false,
                    error = error,
                    clubDetails = detailsResult.getOrNull(),
                    activeSession = sessionResult.getOrNull(),
                    members = membersResult.getOrNull() ?: emptyList()
                )
            }
        }
    }

    fun refresh() {
        Bark.v("Refreshing club data")
        currentClubId?.let { loadClubData(it) }
    }
}