package com.ivangarzab.bookclub.data.remote.source.internal

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

@OptIn(InternalAPI::class)
class ClubDataSource(private val supabase: SupabaseClient) {

    suspend fun get(clubId: String, serverId: String): ClubResponseDto {
        return supabase.functions.invoke("club") {
            method = HttpMethod.Get
            url {
                parameters.append("id", clubId)
                parameters.append("server_id", serverId)
            }
        }.body()
    }

    suspend fun getByChannel(channel: String, serverId: String): ClubResponseDto {
        return supabase.functions.invoke("club") {
            method = HttpMethod.Get
            url {
                parameters.append("discord_channel", channel)
                parameters.append("server_id", serverId)
            }
        }.body()
    }

    suspend fun create(request: CreateClubRequestDto): ClubSuccessResponseDto {
        return supabase.functions.invoke("club") {
            method = HttpMethod.Post
            body = request
        }.body()
    }

    suspend fun update(request: UpdateClubRequestDto): ClubSuccessResponseDto {
        return supabase.functions.invoke("club") {
            method = HttpMethod.Put
            body = request
        }.body()
    }

    suspend fun delete(clubId: String, serverId: String): DeleteResponseDto {
        return supabase.functions.invoke("club") {
            method = HttpMethod.Delete
            url {
                parameters.append("id", clubId)
                parameters.append("server_id", serverId)
            }
        }.body()
    }
}