package com.ivangarzab.bookclub.domain.models

/**
 * Domain model for the (Discord) Server entity.
 */
data class Server(

    val id: String,

    val name: String,

    val clubs: List<Club> = emptyList()
)