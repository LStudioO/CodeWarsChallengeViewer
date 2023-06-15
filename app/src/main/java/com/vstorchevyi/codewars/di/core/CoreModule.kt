package com.vstorchevyi.codewars.di.core

import com.core.utils.platform.environment.EnvironmentInfo
import com.vstorchevyi.codewars.data.AppEnvironmentInfo
import org.koin.dsl.module

fun coreModule() = module {
    single<EnvironmentInfo> { AppEnvironmentInfo() }
}
