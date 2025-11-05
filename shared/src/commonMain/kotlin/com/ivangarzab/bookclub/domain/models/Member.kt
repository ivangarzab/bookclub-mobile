package com.ivangarzab.bookclub.domain.models

data class Member(

    val id: String,

    val name: String,

    val points: Int = 0,

    val booksRead: Int = 0,

    /** The User ID related to Auth. */
    val userId: String? = null,

    val role: String? = null,

    /** List of Club IDs that this member belongs to. **/
    val clubs: List<String> = emptyList()
)