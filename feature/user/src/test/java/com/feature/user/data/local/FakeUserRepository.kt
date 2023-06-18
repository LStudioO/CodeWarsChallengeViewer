package com.feature.user.data.local

import androidx.paging.PagingData
import com.feature.user.domain.model.CompletedChallenge
import com.feature.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

internal class FakeUserRepository(
    private val completedChallengesFlow: Flow<PagingData<CompletedChallenge>>,
) : UserRepository {

    override fun getCompletedChallenges(): Flow<PagingData<CompletedChallenge>> {
        return completedChallengesFlow
    }
}
