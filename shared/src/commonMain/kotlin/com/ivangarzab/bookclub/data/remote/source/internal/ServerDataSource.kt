package com.ivangarzab.bookclub.data.remote.source.internal

import com.ivangarzab.bookclub.data.remote.dtos.CreateServerRequestDto
import com.ivangarzab.bookclub.data.remote.dtos.DeleteResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.ServerResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.ServerSuccessResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.ServersResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.UpdateServerRequestDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.functions.functions
import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import io.ktor.util.InternalAPI

@OptIn(InternalAPI::class)
class ServerDataSource(private val supabase: SupabaseClient) {

    suspend fun getAll(): ServersResponseDto {
        return supabase.functions.invoke("server") {
            method = HttpMethod.Get
        }.body()
    }

    suspend fun get(serverId: String): ServerResponseDto {
        return supabase.functions.invoke("server") {
            method = HttpMethod.Get
            url { parameters.append("id", serverId) }
        }.body()
    }

    suspend fun create(request: CreateServerRequestDto): ServerSuccessResponseDto {
        return supabase.functions.invoke("server") {
            method = HttpMethod.Post
            body = request
        }.body()
    }

    suspend fun update(request: UpdateServerRequestDto): ServerSuccessResponseDto {
        return supabase.functions.invoke("server") {
            method = HttpMethod.Put
            body = request
        }.body()
    }

    suspend fun delete(serverId: String): DeleteResponseDto {
        return supabase.functions.invoke("server") {
            method = HttpMethod.Delete
            url { parameters.append("id", serverId) }
        }.body()
    }
}