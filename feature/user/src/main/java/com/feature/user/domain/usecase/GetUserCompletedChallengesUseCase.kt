package com.feature.user.domain.usecase

import androidx.paging.PagingData
import com.feature.user.domain.model.CompletedChallenge
import com.feature.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

internal class GetUserCompletedChallengesUseCase(
    private val repository: UserRepository,
) {
    operator fun invoke(): Flow<PagingData<CompletedChallenge>> {
        return repository.getCompletedChallenges()
    }
}
