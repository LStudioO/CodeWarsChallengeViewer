package com.feature.user.ui.challenge

import kotlinx.collections.immutable.ImmutableList

data class CompletedChallengeUiModel(
    val id: String,
    val name: String,
    val completedLanguages: ImmutableList<String>,
    val completedAt: String,
)
