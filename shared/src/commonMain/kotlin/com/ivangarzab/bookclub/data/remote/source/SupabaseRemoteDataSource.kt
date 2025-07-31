package com.ivangarzab.bookclub.data.remote.source

import com.ivangarzab.bookclub.data.remote.source.internal.ClubDataSource
import com.ivangarzab.bookclub.data.remote.source.internal.MemberDataSource
import com.ivangarzab.bookclub.data.remote.source.internal.ServerDataSource
import com.ivangarzab.bookclub.data.remote.source.internal.SessionDataSource
import io.github.jan.supabase.SupabaseClient

class SupabaseRemoteDataSource(supabase: SupabaseClient) : SupabaseDataSource {
    override val clubs = ClubDataSource(supabase)
    override val members = MemberDataSource(supabase)
    override val sessions = SessionDataSource(supabase)
    override val servers = ServerDataSource(supabase)
}