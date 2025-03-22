package com.watchtogether.models

data class Member(
    val id: String,
    val name: String,
    val avatarUrl: String? = null
) 