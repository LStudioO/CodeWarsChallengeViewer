package com.feature.challenge_details.data.local

import com.core.utils.AppError
import com.core.utils.NotFoundError
import com.core.utils.functional.Either
import com.core.utils.logger.AppLogger
import com.core.utils.platform.network.HttpError
import com.feature.challenge_details.data.mapper.ChallengeDetailsMapper
import com.feature.challenge_details.data.remote.api.ChallengeApi
import com.feature.challenge_details.domain.model.ChallengeDetails
import com.feature.challenge_details.domain.repository.ChallengeRepository

internal class DefaultChallengeRepository(
    private val api: ChallengeApi,
    private val mapper: ChallengeDetailsMapper,
    private val logger: AppLogger,
) : ChallengeRepository {
    override suspend fun getDetails(id: String): Either<AppError, ChallengeDetails> {
        return api.getDetails(id)
            .ifLeft {
                logger.e(
                    "Error has happened while loading the details of [$id]",
                )
            }
            .ifRight {
                logger.d("Details of [$id] have been loaded successfully")
            }
            .map { dto ->
                mapper.toDomain(dto)
            }
            .mapLeft { error ->
                if (error is HttpError && error.code == 404) {
                    NotFoundError
                } else {
                    AppError(error)
                }
            }
    }
}
