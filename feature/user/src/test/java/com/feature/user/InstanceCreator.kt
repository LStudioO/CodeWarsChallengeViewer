package com.feature.user

import com.feature.user.data.remote.dto.CompletedChallengeDto
import com.feature.user.domain.model.CompletedChallenge
import com.feature.user.domain.model.Language
import kotlinx.datetime.Instant

internal fun createCompletedChallenge(
    id: String = "",
    name: String = "",
    slug: String = "",
    completedLanguages: List<Language> = emptyList(),
    completedAt: Instant = Instant.fromEpochMilliseconds(0),
): CompletedChallenge {
    return CompletedChallenge(
        id = id,
        name = name,
        slug = slug,
        completedLanguages = completedLanguages,
        completedAt = completedAt,
    )
}

internal fun createCompletedChallengeDto(
    id: String = "",
    name: String = "",
    slug: String = "",
    completedLanguages: List<String> = emptyList(),
    completedAt: String = "1970-01-01T00:00:00Z",
): CompletedChallengeDto {
    return CompletedChallengeDto(
        id = id,
        name = name,
        slug = slug,
        completedLanguages = completedLanguages,
        completedAt = completedAt,
    )
}
