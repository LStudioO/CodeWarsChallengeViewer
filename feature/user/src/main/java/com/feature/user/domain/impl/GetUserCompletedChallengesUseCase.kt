package com.feature.user.domain.impl

import androidx.paging.PagingData
import com.feature.user.domain.api.UserRepository
import com.feature.user.domain.model.CompletedChallenge
import kotlinx.coroutines.flow.Flow

internal class GetUserCompletedChallengesUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke(): Flow<PagingData<CompletedChallenge>> {
        return repository.getCompletedChallenges()
    }
}
