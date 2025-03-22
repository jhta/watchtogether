package com.watchtogether.screens.createpoll

import com.watchtogether.models.MovieSource

enum class CreatePollStep {
    ENTER_TITLE,
    SELECT_MOVIES
}

data class CreatePollState(
    val currentStep: CreatePollStep = CreatePollStep.ENTER_TITLE,
    val pollTitle: String = "",
    val selectedMovieIds: Set<String> = emptySet(),
    val selectedSource: MovieSource = MovieSource.WATCH_LATER,
    val searchQuery: String = ""
) 