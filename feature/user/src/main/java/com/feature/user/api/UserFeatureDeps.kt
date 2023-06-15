package com.feature.user.api

import com.core.utils.logger.AppLogger
import com.core.utils.platform.environment.EnvironmentInfo

interface UserFeatureDeps {
    fun provideEnvironmentInfo(): EnvironmentInfo
    fun provideLogger(): AppLogger
}
