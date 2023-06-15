package com.feature.user.domain.impl

import com.core.utils.AppError
import com.core.utils.functional.Either
import com.feature.user.domain.api.UserRepository
import com.feature.user.domain.model.CompletedChallenges

internal class GetUserCompletedChallengesUseCase(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(): Either<AppError, CompletedChallenges> {
        return repository.getCompletedChallenges()
    }
}
