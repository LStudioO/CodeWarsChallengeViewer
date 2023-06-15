package com.feature.user.data.mapper

import com.feature.user.data.remote.dto.CompletedChallengeDto
import com.feature.user.domain.model.CompletedChallenge
import com.feature.user.domain.model.Language
import kotlinx.datetime.toInstant

internal class CompletedChallengeMapper {
    fun toDomain(from: CompletedChallengeDto): CompletedChallenge {
        return CompletedChallenge(
            id = from.id.orEmpty(),
            name = from.name.orEmpty(),
            slug = from.slug.orEmpty(),
            completedLanguages = from.completedLanguages.map { langName ->
                Language(langName)
            },
            completedAt = from.completedAt.toInstant(),
        )
    }
}
