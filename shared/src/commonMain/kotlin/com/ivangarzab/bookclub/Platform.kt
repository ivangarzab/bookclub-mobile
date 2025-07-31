package com.ivangarzab.bookclub

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform