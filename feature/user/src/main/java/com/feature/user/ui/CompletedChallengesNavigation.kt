package com.feature.user.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

const val completedChallengesRoute = "completed_challenges_route"

fun NavController.navigateToCompletedChallenges(navOptions: NavOptions? = null) {
    this.navigate(completedChallengesRoute, navOptions)
}

fun NavGraphBuilder.completedChallengesScreen(
    onChallengeClick: (String) -> Unit,
) {
    composable(route = completedChallengesRoute) {
        CompletedChallengesRoute(
            onChallengeClick = onChallengeClick,
            viewModel = koinNavViewModel(),
        )
    }
}
