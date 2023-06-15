package com.vstorchevyi.codewars

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.feature.user.ui.completedChallengesRoute
import com.vstorchevyi.codewars.navigation.RootNavGraph

@Composable
fun MainApp() {
    val navController = rememberNavController()

    RootNavGraph(
        navController = navController,
        startDestination = completedChallengesRoute,
    )
}
