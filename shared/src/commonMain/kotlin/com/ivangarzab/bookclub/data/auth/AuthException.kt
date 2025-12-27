package com.ivangarzab.bookclub.data.auth

/**
 * Custom [Exception] for authentication operations.
 */
class AuthException(
    override val message: String?
) : Exception()