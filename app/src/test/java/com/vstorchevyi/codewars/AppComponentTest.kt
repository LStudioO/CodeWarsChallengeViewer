package com.vstorchevyi.codewars

import com.vstorchevyi.codewars.di.appComponent
import org.junit.Test
import org.koin.android.test.verify.androidVerify

class AppComponentTest {
    // Static Koin verification
    @Test
    fun checkKoinAppComponent() {
        appComponent().androidVerify()
    }
}
