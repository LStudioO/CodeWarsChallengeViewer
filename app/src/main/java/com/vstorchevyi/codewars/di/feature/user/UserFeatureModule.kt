package com.vstorchevyi.codewars.di.feature.user

import com.core.utils.logger.AppLogger
import com.core.utils.platform.environment.EnvironmentInfo
import com.feature.user.api.UserFeatureDeps
import com.feature.user.di.userFeatureInternalModule
import org.koin.dsl.module

fun userFeatureModule() = module {
    factory<UserFeatureDeps> {
        object : UserFeatureDeps {
            override fun provideEnvironmentInfo(): EnvironmentInfo = get()
            override fun provideLogger(): AppLogger = get()
        }
    }
    includes(userFeatureInternalModule())
}
