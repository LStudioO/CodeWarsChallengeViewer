package com.vstorchevyi.codewars

import android.app.Application
import com.vstorchevyi.codewars.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appComponent())
        }
    }
}
