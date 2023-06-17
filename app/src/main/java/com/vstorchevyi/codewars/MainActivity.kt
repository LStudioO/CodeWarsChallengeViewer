package com.vstorchevyi.codewars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.core.ui.theme.CodeWarsChallengeViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeWarsChallengeViewerTheme {
                MainApp()
            }
        }
    }
}
