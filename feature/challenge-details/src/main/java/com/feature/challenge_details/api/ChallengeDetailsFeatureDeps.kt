package com.feature.challenge_details.api

import com.core.utils.logger.AppLogger
import com.core.utils.platform.environment.EnvironmentInfo

interface ChallengeDetailsDeps {
    fun provideEnvironmentInfo(): EnvironmentInfo
    fun provideLogger(): AppLogger
}
