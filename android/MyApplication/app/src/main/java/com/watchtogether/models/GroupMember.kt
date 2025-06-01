package com.watchtogether.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class MemberRole {
    @SerialName("admin")
    ADMIN,
    @SerialName("member")
    MEMBER
}

@Serializable
data class GroupMember(
    @SerialName("group_id")
    val groupId: Int,
    @SerialName("user_id")
    val userId: String, // UUID format
    val role: MemberRole = MemberRole.MEMBER,
    @SerialName("joined_at")
    val joinedAt: String // ISO format timestamp
) 