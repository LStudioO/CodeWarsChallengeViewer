package com.feature.challenge_details.di

import com.core.utils.platform.network.createApiService
import com.feature.challenge_details.api.ChallengeDetailsDeps
import com.feature.challenge_details.data.local.DefaultChallengeRepository
import com.feature.challenge_details.data.mapper.ChallengeDetailsMapper
import com.feature.challenge_details.data.remote.api.ChallengeApi
import com.feature.challenge_details.domain.repository.ChallengeRepository
import com.feature.challenge_details.domain.usecase.GetChallengeDetailsUseCase
import com.feature.challenge_details.ui.ChallengeDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun challengeDetailsInternalModule() = module {
    single {
        val deps = get<ChallengeDetailsDeps>()
        createApiService<ChallengeApi>(
            baseUrl = deps.provideEnvironmentInfo().hostUrl,
        )
    }
    factory { ChallengeDetailsMapper() }
    factory<ChallengeRepository> {
        val deps = get<ChallengeDetailsDeps>()
        DefaultChallengeRepository(
            api = get(),
            mapper = get(),
            logger = deps.provideLogger(),
        )
    }
    factory {
        GetChallengeDetailsUseCase(
            repository = get(),
        )
    }
    viewModel {
        ChallengeDetailsViewModel(
            savedStateHandle = get(),
            getChallengeDetails = get(),
            mapper = get(),
        )
    }
    factory { com.feature.challenge_details.ui.mapper.ChallengeDetailsMapper() }
}
