package com.feature.user.domain.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.core.utils.logger.AppLogger
import com.feature.user.data.local.UserDataSource
import com.feature.user.data.mapper.CompletedChallengeMapper
import com.feature.user.data.remote.api.UserApi
import com.feature.user.data.remote.source.CompletedChallengesPagingSource
import com.feature.user.domain.api.UserRepository
import com.feature.user.domain.model.CompletedChallenge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val CODEWARS_PAGE_SIZE = 200

internal class InMemoryUserRepository(
    private val userApi: UserApi,
    private val completedChallengeMapper: CompletedChallengeMapper,
    private val userDataSource: UserDataSource,
    private val logger: AppLogger,
) : UserRepository {

    override fun getCompletedChallenges(): Flow<PagingData<CompletedChallenge>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = CODEWARS_PAGE_SIZE,
                initialLoadSize = CODEWARS_PAGE_SIZE,
            ),
            pagingSourceFactory = {
                CompletedChallengesPagingSource(
                    userApi = userApi,
                    userName = userDataSource.user.id,
                    logger = logger,
                )
            },
        )
        return pager.flow.map { pagingData ->
            pagingData.map { challenge ->
                completedChallengeMapper.toDomain(challenge)
            }
        }
    }
}
