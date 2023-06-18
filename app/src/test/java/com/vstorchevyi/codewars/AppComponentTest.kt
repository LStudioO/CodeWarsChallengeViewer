@file:OptIn(KoinExperimentalAPI::class)

package com.vstorchevyi.codewars

import com.vstorchevyi.codewars.di.appComponent
import org.junit.Test
import org.koin.android.test.verify.androidVerify
import org.koin.core.annotation.KoinExperimentalAPI

class AppComponentTest {
    // Static Koin verification
    @Test
    fun checkKoinAppComponent() {
        appComponent().androidVerify()
    }
}
