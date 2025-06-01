package com.watchtogether.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PollOption(
    val id: Int,
    @SerialName("poll_id")
    val pollId: Int,
    @SerialName("movie_title")
    val movieTitle: String,
    @SerialName("movie_id")
    val movieId: String? = null,
    val votes: Int = 0,
    @SerialName("created_at")
    val createdAt: String
) 