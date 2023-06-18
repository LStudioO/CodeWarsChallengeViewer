package com.vstorchevyi.codewars.runner

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.test.runner.AndroidJUnitRunner

class AppTestRunner : AndroidJUnitRunner() {
    // Support MockWebServer
    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    // Support Koin injection
    override fun newApplication(
        cl: ClassLoader?, className: String?, context: Context?,
    ): Application {
        return super.newApplication(
            cl, TestApplication::class.java.name, context,
        )
    }
}
