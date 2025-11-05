package com.ivangarzab.bookclub.data.remote.api

import com.ivangarzab.bookclub.data.remote.dtos.ClubResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.ClubSuccessResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.CreateClubRequestDto
import com.ivangarzab.bookclub.data.remote.dtos.DeleteResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.UpdateClubRequestDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.functions.functions
import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import io.ktor.util.InternalAPI

interface ClubService {
    suspend fun get(clubId: String, serverId: String): ClubResponseDto
    suspend fun getByChannel(channel: String, serverId: String): ClubResponseDto
    suspend fun create(request: CreateClubRequestDto): ClubSuccessResponseDto
    suspend fun update(request: UpdateClubRequestDto): ClubSuccessResponseDto
    suspend fun delete(clubId: String, serverId: String): DeleteResponseDto
}

@OptIn(InternalAPI::class)
internal class ClubServiceImpl(private val supabase: SupabaseClient) : ClubService {

    override suspend fun get(clubId: String, serverId: String): ClubResponseDto {
        return supabase.functions.invoke("club") {
            method = HttpMethod.Get
            url {
                parameters.append("id", clubId)
                parameters.append("server_id", serverId)
            }
        }.body()
    }

    override suspend fun getByChannel(channel: String, serverId: String): ClubResponseDto {
        return supabase.functions.invoke("club") {
            method = HttpMethod.Get
            url {
                parameters.append("discord_channel", channel)
                parameters.append("server_id", serverId)
            }
        }.body()
    }

    override suspend fun create(request: CreateClubRequestDto): ClubSuccessResponseDto {
        return supabase.functions.invoke("club") {
            method = HttpMethod.Post
            body = request
        }.body()
    }

    override suspend fun update(request: UpdateClubRequestDto): ClubSuccessResponseDto {
        return supabase.functions.invoke("club") {
            method = HttpMethod.Put
            body = request
        }.body()
    }

    override suspend fun delete(clubId: String, serverId: String): DeleteResponseDto {
        return supabase.functions.invoke("club") {
            method = HttpMethod.Delete
            url {
                parameters.append("id", clubId)
                parameters.append("server_id", serverId)
            }
        }.body()
    }
}