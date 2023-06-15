package com.feature.user.di

import com.core.utils.platform.network.createApiService
import com.feature.user.api.UserFeatureDeps
import com.feature.user.data.mapper.UserMapper
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
    factory { UserMapper() }
    factory<UserRepository> {
        InMemoryUserRepository(
            userApi = get(),
            userMapper = get(),
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
