package com.feature.challenge_details.data.mapper

import com.feature.challenge_details.data.remote.dto.ActionByDto
import com.feature.challenge_details.data.remote.dto.ChallengeDetailsDto
import com.feature.challenge_details.data.remote.dto.RankDto
import com.feature.challenge_details.domain.model.*
import kotlinx.datetime.Instant

internal class ChallengeDetailsMapper {
    fun toDomain(from: ChallengeDetailsDto): ChallengeDetails {
        return ChallengeDetails(
            category = from.category.orEmpty(),
            description = from.description.orEmpty(),
            id = from.id,
            languages = from.languages?.map { Language(name = it) }.orEmpty(),
            name = from.name.orEmpty(),
            tags = from.tags?.map { Tag(name = it) }.orEmpty(),
            stats = ChallengeStats(
                totalAttempts = from.totalAttempts ?: 0,
                totalCompleted = from.totalCompleted ?: 0,
                totalStars = from.totalStars ?: 0,
                voteScore = from.voteScore ?: 0,
                unresolvedIssues = from.unresolved?.issues ?: 0,
                unresolvedSuggestions = from.unresolved?.suggestions ?: 0,
            ),
            rank = from.rank?.toDomain(),
            createdBy = from.createdBy?.toDomain(from.createdAt),
            approvedBy = from.approvedBy?.toDomain(from.approvedAt),
        )
    }

    private fun RankDto.toDomain(): Rank {
        val color = when (this.color) {
            "white" -> RankColor.White
            "yellow" -> RankColor.Yellow
            "blue" -> RankColor.Blue
            "purple" -> RankColor.Purple
            else -> RankColor.Unknown
        }

        return Rank(
            value = this.rankName.orEmpty(),
            color = color,
        )
    }

    private fun ActionByDto.toDomain(date: String?): ActionBy {
        return ActionBy(
            name = this.username,
            url = this.url,
            date = date?.let { Instant.parse(it) },
        )
    }
}
