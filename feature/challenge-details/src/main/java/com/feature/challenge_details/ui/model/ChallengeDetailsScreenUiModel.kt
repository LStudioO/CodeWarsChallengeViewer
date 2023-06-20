package com.feature.challenge_details.ui.model

internal data class ChallengeDetailsScreenUiModel(
    val isLoading: Boolean,
    val error: ChallengeDetailsError,
    val challengeDetails: ChallengeDetailsUiModel?,
    val challengeName: String,
)

internal sealed class ChallengeDetailsError {
    object None : ChallengeDetailsError()
    object NetworkError : ChallengeDetailsError()
    object NotFound : ChallengeDetailsError()
}
