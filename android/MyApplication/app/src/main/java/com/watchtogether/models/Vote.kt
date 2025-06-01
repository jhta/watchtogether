package com.watchtogether.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Vote(
    @SerialName("poll_id")
    val pollId: Int,
    @SerialName("option_id")
    val optionId: Int,
    @SerialName("user_id")
    val userId: String, // UUID format
    @SerialName("created_at")
    val createdAt: String // ISO format timestamp
) 