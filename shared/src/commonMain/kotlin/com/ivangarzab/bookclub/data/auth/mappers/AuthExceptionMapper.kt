package com.ivangarzab.bookclub.data.auth.mappers

import com.ivangarzab.bookclub.data.auth.AuthException

/**
 * Maps any [Exception] into an [AuthException].
 */
fun Exception.toAuthErrorMessage(): AuthException = AuthException(
    message = this.message?.let { message ->
        when {
            // Invalid credentials
            message.contains("Invalid login credentials", ignoreCase = true) ->
                "Invalid email or password"

            message.contains("Email not confirmed", ignoreCase = true) ->
                "Please verify your email address"
            // Network errors
            message.contains("Unable to resolve host", ignoreCase = true) ||
                    message.contains("Failed to connect", ignoreCase = true) ->
                "No internet connection"
            // Rate limiting
            message.contains("Email rate limit exceeded", ignoreCase = true) ->
                "Too many attempts. Please try again later"
            // User not found
            message.contains("User not found", ignoreCase = true) ->
                "No account found with this email"
            // Weak password (for sign up)
            message.contains("Password should be at least", ignoreCase = true) ->
                "Password is too weak"
            // Email already exists (for sign up)
            message.contains("User already registered", ignoreCase = true) ->
                "An account with this email already exists"
            // Generic fallback
            else -> "Authentication failed. Please try again"
        }
    } ?: "An unexpected error occurred"
)