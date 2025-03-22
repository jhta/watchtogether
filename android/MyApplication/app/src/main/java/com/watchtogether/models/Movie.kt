package com.watchtogether.models

data class Movie(
    val id: String,
    val title: String,
    val year: String,
    val posterUrl: String? = null,
    val rating: Float = 0f,
    val description: String = ""
)

enum class MovieSource {
    WATCH_LATER,
    GROUP_FAVORITES,
    SEARCH_RESULTS
} 