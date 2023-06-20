package com.feature.challenge_details.domain.model

internal data class ChallengeStats(
    val totalAttempts: Int,
    val totalCompleted: Int,
    val totalStars: Int,
    val voteScore: Int,
    val unresolvedIssues: Int,
    val unresolvedSuggestions: Int,
) {
    val successPercent: Int
        get() {
            if (totalAttempts == 0) return 0
            if (totalAttempts < 0 || totalCompleted < 0) return 0
            return ((totalCompleted / totalAttempts.toDouble()) * 100).toInt()
                .coerceAtLeast(0)
                .coerceAtMost(100)
        }
}
