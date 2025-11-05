package com.ivangarzab.bookclub.data.remote.api

import com.ivangarzab.bookclub.data.remote.dtos.CreateSessionRequestDto
import com.ivangarzab.bookclub.data.remote.dtos.DeleteResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.SessionResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.SessionSuccessResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.UpdateSessionRequestDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.functions.functions
import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import io.ktor.util.InternalAPI

@OptIn(InternalAPI::class)
class SessionService(private val supabase: SupabaseClient) {

    suspend fun get(sessionId: String): SessionResponseDto {
        return supabase.functions.invoke("session") {
            method = HttpMethod.Get
            url { parameters.append("id", sessionId) }
        }.body()
    }

    suspend fun create(request: CreateSessionRequestDto): SessionSuccessResponseDto {
        return supabase.functions.invoke("session") {
            method = HttpMethod.Post
            body = request
        }.body()
    }

    suspend fun update(request: UpdateSessionRequestDto): SessionSuccessResponseDto {
        return supabase.functions.invoke("session") {
            method = HttpMethod.Put
            body = request
        }.body()
    }

    suspend fun delete(sessionId: String): DeleteResponseDto {
        return supabase.functions.invoke("session") {
            method = HttpMethod.Delete
            url { parameters.append("id", sessionId) }
        }.body()
    }
}