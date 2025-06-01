package com.watchtogether.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class PollStatus {
    @SerialName("active")
    ACTIVE,
    @SerialName("completed")
    COMPLETED,
    @SerialName("canceled")
    CANCELED
}

@Serializable
data class Poll(
    val id: Int, // Changed from String to Int to match DB schema
    @SerialName("group_id")
    val groupId: Int,
    val title: String,
    val description: String? = null,
    val status: PollStatus,
    @SerialName("created_at")
    val createdAt: String, // ISO format timestamp
    @SerialName("created_by")
    val createdBy: String, // UUID of creator
    @SerialName("ends_at")
    val endsAt: String? = null // Updated from end_date to ends_at
) 