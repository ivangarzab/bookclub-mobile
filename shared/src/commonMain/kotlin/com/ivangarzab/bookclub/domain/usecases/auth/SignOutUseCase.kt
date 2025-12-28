package com.ivangarzab.bookclub.domain.usecases.auth

import com.ivangarzab.bookclub.data.auth.AuthRepository

/**
 *
 */
class SignOutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.signOut()
    }
}