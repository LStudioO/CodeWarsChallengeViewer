package com.vstorchevyi.codewars.di.core

import com.core.utils.logger.AppLogger
import com.core.utils.platform.environment.EnvironmentInfo
import com.core.utils.platform.logger.TimberLogger
import com.core.utils.platform.network.ConnectivityMonitor
import com.core.utils.platform.network.NetworkMonitor
import com.vstorchevyi.codewars.data.AppEnvironmentInfo
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun coreModule() = module {
    single<EnvironmentInfo> { AppEnvironmentInfo() }
    single<AppLogger> { TimberLogger() }
    single<NetworkMonitor> { ConnectivityMonitor(context = androidContext()) }
}
