package com.feature.challenge_details.domain.repository

import com.core.utils.AppError
import com.core.utils.functional.Either
import com.feature.challenge_details.domain.model.ChallengeDetails

internal interface ChallengeRepository {
    suspend fun getDetails(id: String): Either<AppError, ChallengeDetails>
}
