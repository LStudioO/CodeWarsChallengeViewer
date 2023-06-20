package com.feature.challenge_details.ui.model

import com.feature.challenge_details.domain.model.ChallengeStats
import kotlinx.collections.immutable.ImmutableList

internal data class ChallengeDetailsUiModel(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val rank: RankUiModel?,
    val createdBy: ActionByUiModel?,
    val approvedBy: ActionByUiModel?,
    val languages: ImmutableList<String>,
    val tags: ImmutableList<String>,
    val stats: ChallengeStats,
)
