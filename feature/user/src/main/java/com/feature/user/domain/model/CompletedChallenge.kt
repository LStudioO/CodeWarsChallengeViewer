package com.feature.user.domain.model

import kotlinx.datetime.Instant

internal data class CompletedChallenge(
    val id: String,
    val name: String,
    val completedLanguages: List<Language>,
    val completedAt: Instant,
)
