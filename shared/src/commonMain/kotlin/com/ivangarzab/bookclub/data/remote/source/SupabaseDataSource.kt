package com.ivangarzab.bookclub.data.remote.source

import com.ivangarzab.bookclub.data.remote.source.internal.ClubDataSource
import com.ivangarzab.bookclub.data.remote.source.internal.MemberDataSource
import com.ivangarzab.bookclub.data.remote.source.internal.ServerDataSource
import com.ivangarzab.bookclub.data.remote.source.internal.SessionDataSource

interface SupabaseDataSource {
    val clubs: ClubDataSource
    val members: MemberDataSource
    val sessions: SessionDataSource
    val servers: ServerDataSource
}