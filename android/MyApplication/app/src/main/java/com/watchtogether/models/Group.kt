package com.watchtogether.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Group(
    val id: Int,
    val name: String,
    @SerialName("participant_count")
    val participantCount: Int = 1
) 