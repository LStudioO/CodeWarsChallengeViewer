package com.vstorchevyi.codewars

import android.content.Intent
import android.net.Uri
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.vstorchevyi.codewars.di.KoinTestRule
import com.vstorchevyi.codewars.di.appComponent
import com.vstorchevyi.codewars.extension.launch
import org.junit.Rule
import org.junit.Test

class AppDeepLinkingTest {
    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(
        modules = listOf(appComponent()),
    )

    @get:Rule
    val composeRule = createEmptyComposeRule()

    @Test
    fun details_deeplink() = composeRule.launch<MainActivity>(
        intentFactory = {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://vstorchevyi-cw.com/1/taskTestName"),
                it,
                MainActivity::class.java,
            )
        },
        onAfterLaunched = {
            onNodeWithText("taskTestName")
                .assertExists()
        },
    )
}
