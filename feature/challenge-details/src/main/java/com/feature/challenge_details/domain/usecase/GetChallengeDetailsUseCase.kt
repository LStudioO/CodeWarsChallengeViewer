package com.feature.challenge_details.domain.usecase

import com.core.utils.AppError
import com.core.utils.functional.Either
import com.feature.challenge_details.domain.model.ChallengeDetails
import com.feature.challenge_details.domain.repository.ChallengeRepository

internal class GetChallengeDetailsUseCase(
    private val repository: ChallengeRepository,
) {
    suspend operator fun invoke(id: String): Either<AppError, ChallengeDetails> {
        return repository.getDetails(id)
    }
}
