package com.feature.challenge_details.util

import com.feature.challenge_details.data.remote.dto.ActionByDto
import com.feature.challenge_details.data.remote.dto.ChallengeDetailsDto
import com.feature.challenge_details.data.remote.dto.RankDto
import com.feature.challenge_details.data.remote.dto.UnresolvedDto
import com.feature.challenge_details.domain.model.*
import com.feature.challenge_details.ui.model.ChallengeDetailsError
import com.feature.challenge_details.ui.model.ChallengeDetailsScreenUiModel
import com.feature.challenge_details.ui.model.ChallengeDetailsUiModel

internal fun createChallengeDetails(
    id: String = "",
    name: String = "",
    category: String = "",
    description: String = "",
    languages: List<Language> = emptyList(),
    tags: List<Tag> = emptyList(),
    rank: Rank? = null,
    createdBy: ActionBy? = null,
    approvedBy: ActionBy? = null,
    stats: ChallengeStats = ChallengeStats(
        totalStars = 0,
        totalCompleted = 0,
        totalAttempts = 0,
        voteScore = 0,
        unresolvedIssues = 0,
        unresolvedSuggestions = 0,
    ),
): ChallengeDetails {
    return ChallengeDetails(
        id = id,
        name = name,
        category = category,
        description = description,
        languages = languages,
        tags = tags,
        rank = rank,
        createdBy = createdBy,
        approvedBy = approvedBy,
        stats = stats,
    )
}

internal fun createChallengeDetailsDto(
    id: String = "",
    name: String? = null,
    slug: String? = null,
    category: String? = null,
    publishedAt: String? = null,
    approvedAt: String? = null,
    languages: List<String>? = null,
    url: String? = null,
    rank: RankDto? = null,
    createdAt: String? = null,
    createdBy: ActionByDto? = null,
    approvedBy: ActionByDto? = null,
    description: String? = null,
    totalAttempts: Int? = null,
    totalCompleted: Int? = null,
    totalStars: Int? = null,
    voteScore: Int? = null,
    tags: List<String>? = null,
    contributorsWanted: Boolean? = null,
    unresolved: UnresolvedDto? = null,
): ChallengeDetailsDto {
    return ChallengeDetailsDto(
        id = id,
        name = name,
        slug = slug,
        category = category,
        description = description,
        languages = languages,
        publishedAt = publishedAt,
        tags = tags,
        totalStars = totalStars,
        totalCompleted = totalCompleted,
        totalAttempts = totalAttempts,
        approvedAt = approvedAt,
        url = url,
        rank = rank,
        createdAt = createdAt,
        createdBy = createdBy,
        approvedBy = approvedBy,
        voteScore = voteScore,
        contributorsWanted = contributorsWanted,
        unresolved = unresolved,
    )
}

internal fun createChallengeDetailsScreenUiModel(
    isLoading: Boolean = false,
    error: ChallengeDetailsError = ChallengeDetailsError.None,
    challengeDetails: ChallengeDetailsUiModel? = null,
    challengeName: String = "",
): ChallengeDetailsScreenUiModel {
    return ChallengeDetailsScreenUiModel(
        isLoading = isLoading,
        error = error,
        challengeDetails = challengeDetails,
        challengeName = challengeName,
    )
}
