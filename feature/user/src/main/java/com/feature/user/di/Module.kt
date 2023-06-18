package com.feature.user.di

import com.core.utils.platform.network.createApiService
import com.feature.user.api.UserFeatureDeps
import com.feature.user.data.local.InMemoryUserRepository
import com.feature.user.data.local.data_source.InMemoryUserDataSource
import com.feature.user.data.local.data_source.UserDataSource
import com.feature.user.data.mapper.CompletedChallengeMapper
import com.feature.user.data.remote.api.UserApi
import com.feature.user.domain.repository.UserRepository
import com.feature.user.domain.usecase.GetUserCompletedChallengesUseCase
import com.feature.user.ui.challenge.CompletedChallengesViewModel
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
            mapper = get(),
        )
    }
    factory { com.feature.user.ui.challenge.mapper.CompletedChallengeMapper() }
}
