package com.feature.challenge_details.domain.model

internal data class ChallengeDetails(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val languages: List<Language>,
    val tags: List<Tag>,
    val rank: Rank?,
    val createdBy: ActionBy?,
    val approvedBy: ActionBy?,
    val stats: ChallengeStats,
)
