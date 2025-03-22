package com.watchtogether.models

import java.util.Date

enum class PollStatus {
    ACTIVE,
    COMPLETED
}

data class Poll(
    val id: String,
    val title: String,
    val description: String,
    val status: PollStatus,
    val createdAt: Date,
    val endDate: Date? = null
) 