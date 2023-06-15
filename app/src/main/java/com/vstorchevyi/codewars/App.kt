package com.vstorchevyi.codewars

import android.app.Application
import com.core.utils.platform.logger.TimberDebugTree
import com.vstorchevyi.codewars.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogging()
        startKoin {
            androidContext(this@App)
            modules(appComponent())
        }
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(TimberDebugTree())
        }
    }
}
