package com.feature.user.di

import com.core.utils.platform.network.createApiService
import com.feature.user.api.UserFeatureDeps
import com.feature.user.data.local.InMemoryUserDataSource
import com.feature.user.data.local.UserDataSource
import com.feature.user.data.mapper.CompletedChallengeMapper
import com.feature.user.data.remote.api.UserApi
import com.feature.user.domain.api.UserRepository
import com.feature.user.domain.impl.GetUserCompletedChallengesUseCase
import com.feature.user.domain.impl.InMemoryUserRepository
import com.feature.user.ui.CompletedChallengesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun userFeatureInternalModule() = module {
    single {
        val deps = get<UserFeatureDeps>()
        createApiService<UserApi>(
            baseUrl = deps.provideEnvironmentInfo().hostUrl,
        )
    }
    factory<UserDataSource> { InMemoryUserDataSource() }
    factory { CompletedChallengeMapper() }
    factory<UserRepository> {
        val deps = get<UserFeatureDeps>()
        InMemoryUserRepository(
            userApi = get(),
            completedChallengeMapper = get(),
            userDataSource = get(),
            logger = deps.provideLogger(),
        )
    }
    factory {
        GetUserCompletedChallengesUseCase(
            repository = get(),
        )
    }
    viewModel {
        CompletedChallengesViewModel(
            getCompletedChallenges = get(),
        )
    }
}
