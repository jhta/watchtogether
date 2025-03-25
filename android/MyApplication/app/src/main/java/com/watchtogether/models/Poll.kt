package com.watchtogether.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import java.util.Date

@Serializable
enum class PollStatus {
    @SerialName("draft")
    DRAFT,
    @SerialName("active")
    ACTIVE,
    @SerialName("closed")
    CLOSED
}

@Serializable
data class Poll(
    val id: String,
    @SerialName("group_id")
    val groupId: Int,
    val title: String,
    val description: String? = null,
    val status: PollStatus,
    @SerialName("created_at")
    val createdAt: String, // Using String for timestamp as it comes in ISO format
    @SerialName("end_date")
    val endDate: String? = null
) 