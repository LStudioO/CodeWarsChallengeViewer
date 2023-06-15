package com.feature.user.repository

import com.core.utils.AppError
import com.core.utils.functional.Either
import com.feature.user.domain.api.UserRepository
import com.feature.user.domain.model.CompletedChallenges
import com.feature.user.domain.model.User

internal class FakeUserRepository(
    private val completedChallengesState: Either<AppError, CompletedChallenges>,
) : UserRepository {
    override fun getUser(): User {
        return User("1234")
    }

    override suspend fun getCompletedChallenges(): Either<AppError, CompletedChallenges> {
        return completedChallengesState
    }
}
