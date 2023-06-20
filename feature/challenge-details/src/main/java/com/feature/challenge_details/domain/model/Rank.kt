package com.feature.challenge_details.domain.model

internal data class Rank(
    val value: String,
    val color: RankColor,
)

internal enum class RankColor {
    White,
    Yellow,
    Blue,
    Purple,
    Unknown
}
