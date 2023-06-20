package com.feature.challenge_details.domain.model

import kotlinx.datetime.Instant

internal data class ActionBy(
    val name: String,
    val url: String?,
    val date: Instant?,
)
