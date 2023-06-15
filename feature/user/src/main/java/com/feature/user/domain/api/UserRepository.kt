package com.feature.user.domain.api

import com.core.utils.AppError
import com.core.utils.functional.Either
import com.feature.user.domain.model.CompletedChallenges
import com.feature.user.domain.model.User

internal interface UserRepository {
    fun getUser(): User?
    suspend fun getCompletedChallenges(): Either<AppError, CompletedChallenges>
}
