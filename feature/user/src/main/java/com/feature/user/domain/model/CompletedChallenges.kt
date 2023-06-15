package com.feature.user.domain.model

internal data class CompletedChallenges(
    val challenges: List<CompletedChallenge>,
) {
    companion object {
        fun emptyInstance() = CompletedChallenges(challenges = emptyList())
    }
}
