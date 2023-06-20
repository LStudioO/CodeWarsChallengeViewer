package com.vstorchevyi.codewars.di.feature.completed_challendge

import com.core.utils.logger.AppLogger
import com.core.utils.platform.environment.EnvironmentInfo
import com.feature.challenge_details.api.ChallengeDetailsDeps
import com.feature.challenge_details.di.challengeDetailsInternalModule
import org.koin.dsl.module

fun challengeDetailsFeatureModule() = module {
    factory<ChallengeDetailsDeps> {
        object : ChallengeDetailsDeps {
            override fun provideEnvironmentInfo(): EnvironmentInfo = get()
            override fun provideLogger(): AppLogger = get()
        }
    }
    includes(challengeDetailsInternalModule())
}
