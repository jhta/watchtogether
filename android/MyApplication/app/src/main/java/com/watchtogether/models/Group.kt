package com.watchtogether.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Group(
    val id: Int,
    val name: String,
    val description: String? = null,
    @SerialName("created_at")
    val createdAt: String, // ISO format timestamp
    @SerialName("created_by")
    val createdBy: String, // UUID of creator
    @SerialName("updated_at")
    val updatedAt: String, // ISO format timestamp
    @SerialName("participant_count")
    val participantCount: Int = 1
) 