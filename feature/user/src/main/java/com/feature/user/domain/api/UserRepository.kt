package com.feature.user.domain.api

import androidx.paging.PagingData
import com.feature.user.domain.model.CompletedChallenge
import kotlinx.coroutines.flow.Flow

internal interface UserRepository {
    fun getCompletedChallenges(): Flow<PagingData<CompletedChallenge>>
}
