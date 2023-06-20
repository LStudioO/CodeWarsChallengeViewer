package com.feature.challenge_details.ui.mapper

import androidx.compose.ui.graphics.Color
import com.feature.challenge_details.domain.model.ChallengeDetails
import com.feature.challenge_details.domain.model.Rank
import com.feature.challenge_details.domain.model.RankColor
import com.feature.challenge_details.ui.model.RankUiModel
import com.feature.challenge_details.ui.model.ActionByUiModel
import com.feature.challenge_details.ui.model.ChallengeDetailsUiModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

internal class ChallengeDetailsMapper {
    private val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)

    fun toUi(from: ChallengeDetails): ChallengeDetailsUiModel {
        return ChallengeDetailsUiModel(
            id = from.id,
            name = from.name,
            description = from.description,
            rank = from.rank?.toUi(),
            createdBy = from.createdBy?.let { model ->
                ActionByUiModel(
                    name = model.name,
                    url = model.url,
                    date = model.date?.format(),
                )
            },
            approvedBy = from.createdBy?.let { model ->
                ActionByUiModel(
                    name = model.name,
                    url = model.url,
                    date = model.date?.format(),
                )
            },
            category = from.category,
            languages = from.languages.map { it.name }.toPersistentList(),
            tags = from.tags.map { it.name }.toPersistentList(),
            stats = from.stats,
        )
    }

    private fun Instant.format(): String {
        val localDateTime = this.toLocalDateTime(
            TimeZone.currentSystemDefault(),
        ).toJavaLocalDateTime()
        return dateFormatter.format(localDateTime)
    }

    private fun Rank.toUi(): RankUiModel? {
        val color = when (this.color) {
            RankColor.White -> Color(0xFFCAC4CF)
            RankColor.Yellow -> Color.Yellow
            RankColor.Blue -> Color.Blue
            RankColor.Purple -> Color.Cyan
            RankColor.Unknown -> return null
        }
        if (this.value == "") return null
        return RankUiModel(
            name = value,
            color = color,
        )
    }
}
