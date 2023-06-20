package com.feature.challenge_details.data.local

import com.core.utils.AppError
import com.core.utils.functional.Either
import com.feature.challenge_details.domain.model.ChallengeDetails
import com.feature.challenge_details.domain.repository.ChallengeRepository

internal class ChallengeRepositoryFake : ChallengeRepository {
    private var detailsResponse: Either<AppError, ChallengeDetails> = Either.left(AppError())

    fun setDetailsResponse(response: Either<AppError, ChallengeDetails>) {
        detailsResponse = response
    }

    override suspend fun getDetails(id: String): Either<AppError, ChallengeDetails> {
        return detailsResponse
    }
}
