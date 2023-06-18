package com.feature.user.ui.challenge.mapper

import com.feature.user.domain.model.CompletedChallenge
import com.feature.user.ui.challenge.CompletedChallengeUiModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

internal class CompletedChallengeMapper {
    private val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(
        FormatStyle.MEDIUM,
        FormatStyle.MEDIUM,
    )

    fun toUi(from: CompletedChallenge): CompletedChallengeUiModel {
        val localDateTime = from.completedAt.toLocalDateTime(
            TimeZone.currentSystemDefault(),
        ).toJavaLocalDateTime()
        val completedAtFormatted = dateFormatter.format(localDateTime)
        return CompletedChallengeUiModel(
            id = from.id,
            name = from.name,
            completedLanguages = from.completedLanguages.map { it.name }.toImmutableList(),
            completedAt = completedAtFormatted,
        )
    }
}
