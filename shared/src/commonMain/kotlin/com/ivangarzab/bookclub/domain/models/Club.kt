package com.ivangarzab.bookclub.domain.models

/**
 * Domain model for the Club entity.
 */
data class Club(

    val id: String,

    val name: String,

    /** Discord channel Snowflake ID that this Club is related to. **/
    val discordChannel: String? = null,

    /** Server ID that this Club belongs to. **/
    val serverId: String? = null,

    /** List of [Session]s that this Club is related to. **/
    val sessions: List<Session> = emptyList(),

    /** List of [Member]s that this Club is related to. **/
    val members: List<Member> = emptyList()
)
