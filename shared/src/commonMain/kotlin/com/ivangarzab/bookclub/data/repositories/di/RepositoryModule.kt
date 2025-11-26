package com.ivangarzab.bookclub.data.repositories.di

import com.ivangarzab.bookclub.data.remote.source.ClubRemoteDataSource
import com.ivangarzab.bookclub.data.remote.source.MemberRemoteDataSource
import com.ivangarzab.bookclub.data.remote.source.ServerRemoteDataSource
import com.ivangarzab.bookclub.data.remote.source.SessionRemoteDataSource
import com.ivangarzab.bookclub.data.repositories.ClubRepository
import com.ivangarzab.bookclub.data.repositories.ClubRepositoryImpl
import com.ivangarzab.bookclub.data.repositories.MemberRepository
import com.ivangarzab.bookclub.data.repositories.MemberRepositoryImpl
import com.ivangarzab.bookclub.data.repositories.ServerRepository
import com.ivangarzab.bookclub.data.repositories.ServerRepositoryImpl
import com.ivangarzab.bookclub.data.repositories.SessionRepository
import com.ivangarzab.bookclub.data.repositories.SessionRepositoryImpl
import org.koin.dsl.module

/**
 * [org.koin.core.Koin] module for the repository layer.
 *
 * This module provides repository instances that abstract data access.
 * Currently, repositories delegate to remote data sources, but can be extended
 * to include local data sources for caching and offline support.
 */
val repositoryModule = module {

    // Repositories
    single<ServerRepository> { ServerRepositoryImpl(get<ServerRemoteDataSource>()) }
    single<ClubRepository> { ClubRepositoryImpl(get<ClubRemoteDataSource>()) }
    single<MemberRepository> { MemberRepositoryImpl(get<MemberRemoteDataSource>()) }
    single<SessionRepository> { SessionRepositoryImpl(get<SessionRemoteDataSource>()) }
}
