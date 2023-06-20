package com.vstorchevyi.codewars.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.feature.challenge_details.ui.challengeDetailsScreen
import com.feature.challenge_details.ui.navigateToChallengeDetails
import com.feature.user.ui.challenge.completedChallengesScreen

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
        val navigateBack: () -> Unit = {
            navController.navigateUp()
        }

        completedChallengesScreen(
            onChallengeClick = { id, name ->
                navController.navigateToChallengeDetails(
                    id = id,
                    name = name,
                )
            },
        )

        challengeDetailsScreen(
            onBackClick = navigateBack,
        )
    }
}
