package com.vstorchevyi.codewars.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.feature.user.ui.completedChallengesScreen

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        completedChallengesScreen(
            onChallengeClick = {
                // TODO: navigate to the challenge details
            },
        )
    }
}
