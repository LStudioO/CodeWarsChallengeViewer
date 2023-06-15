package com.feature.user.domain.impl

import com.core.utils.AppError
import com.core.utils.functional.Either
import com.feature.user.data.mapper.UserMapper
import com.feature.user.data.remote.api.UserApi
import com.feature.user.domain.api.UserRepository
import com.feature.user.domain.model.CompletedChallenges
import com.feature.user.domain.model.User

internal class InMemoryUserRepository(
    private val userApi: UserApi,
    private val userMapper: UserMapper,
) : UserRepository {
    override fun getUser(): User {
        return User("g964")
    }

    override suspend fun getCompletedChallenges(): Either<AppError, CompletedChallenges> {
        val user = getUser()
        return userApi.getCompletedChallenges(username = user.id)
            .map { userMapper.toDomain(from = it) }
            .mapLeft { AppError(it) }
    }
}
