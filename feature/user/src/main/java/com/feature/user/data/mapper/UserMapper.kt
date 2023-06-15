package com.feature.user.data.mapper

import com.feature.user.data.remote.dto.CompletedChallengesDto
import com.feature.user.domain.model.CompletedChallenge
import com.feature.user.domain.model.CompletedChallenges
import com.feature.user.domain.model.Language
import kotlinx.datetime.toInstant

internal class UserMapper {
    fun toDomain(from: CompletedChallengesDto): CompletedChallenges {
        return CompletedChallenges(
            challenges = from.data.map { challenge ->
                CompletedChallenge(
                    id = challenge.id,
                    name = challenge.name,
                    slug = challenge.slug,
                    completedLanguages = challenge.completedLanguages.map { langName ->
                        Language(langName)
                    },
                    completedAt = challenge.completedAt.toInstant(),
                )
            },
        )
    }
}
