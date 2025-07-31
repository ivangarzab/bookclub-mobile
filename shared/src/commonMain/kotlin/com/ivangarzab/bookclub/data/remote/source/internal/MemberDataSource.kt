package com.ivangarzab.bookclub.data.remote.source.internal

import com.ivangarzab.bookclub.data.remote.dtos.CreateMemberRequestDto
import com.ivangarzab.bookclub.data.remote.dtos.DeleteResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.MemberResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.MemberSuccessResponseDto
import com.ivangarzab.bookclub.data.remote.dtos.UpdateMemberRequestDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.functions.functions
import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import io.ktor.util.InternalAPI

@OptIn(InternalAPI::class)
class MemberDataSource(private val supabase: SupabaseClient) {

    suspend fun get(memberId: String): MemberResponseDto {
        return supabase.functions.invoke("member") {
            method = HttpMethod.Get
            url { parameters.append("id", memberId) }
        }.body()
    }

    suspend fun getByUserId(userId: String): MemberResponseDto {
        return supabase.functions.invoke("member") {
            method = HttpMethod.Get
            url { parameters.append("user_id", userId) }
        }.body()
    }

    suspend fun create(request: CreateMemberRequestDto): MemberSuccessResponseDto {
        return supabase.functions.invoke("member") {
            method = HttpMethod.Post
            body = request
        }.body()
    }

    suspend fun update(request: UpdateMemberRequestDto): MemberSuccessResponseDto {
        return supabase.functions.invoke("member") {
            method = HttpMethod.Put
            body = request
        }.body()
    }

    suspend fun delete(memberId: String): DeleteResponseDto {
        return supabase.functions.invoke("member") {
            method = HttpMethod.Delete
            url { parameters.append("id", memberId) }
        }.body()
    }
}