package com.feature.user.api

import com.core.utils.platform.environment.EnvironmentInfo

interface UserFeatureDeps {
    fun provideEnvironmentInfo(): EnvironmentInfo
}
