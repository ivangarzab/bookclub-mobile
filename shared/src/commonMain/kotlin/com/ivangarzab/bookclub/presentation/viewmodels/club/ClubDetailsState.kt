package com.ivangarzab.bookclub.presentation.viewmodels.club

import com.ivangarzab.bookclub.presentation.models.ActiveSessionDetails
import com.ivangarzab.bookclub.presentation.models.ClubDetails
import com.ivangarzab.bookclub.presentation.models.MemberListItemInfo

data class ClubDetailsState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val clubDetails: ClubDetails? = null,
    val activeSession: ActiveSessionDetails? = null,
    val members: List<MemberListItemInfo> = emptyList()
)