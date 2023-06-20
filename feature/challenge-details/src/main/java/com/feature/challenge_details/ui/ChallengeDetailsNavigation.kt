@file:OptIn(ExperimentalAnimationApi::class)

package com.feature.challenge_details.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.*
import androidx.navigation.compose.composable
import org.koin.androidx.compose.navigation.koinNavViewModel

const val CHALLENGE_ID = "challengeId"
const val CHALLENGE_NAME = "challengeName"
const val CHALLENGE_ROUTE_START = "challenge_details_route"

const val challengeDetailsRoute = "$CHALLENGE_ROUTE_START/{$CHALLENGE_ID}/{$CHALLENGE_NAME}"

private fun buildRoute(
    id: String,
    name: String,
): String {
    return "$CHALLENGE_ROUTE_START/$id/${name.makeSafeForNavigation()}"
}

fun NavController.navigateToChallengeDetails(
    id: String,
    name: String,
    navOptions: NavOptions? = null,
) {
    this.navigate(buildRoute(id, name), navOptions)
}

fun NavGraphBuilder.challengeDetailsScreen(
    onBackClick: () -> Unit,
) {
    composable(
        arguments = listOf(
            navArgument(CHALLENGE_ID) { type = NavType.StringType },
            navArgument(CHALLENGE_NAME) { type = NavType.StringType },
        ),
        route = challengeDetailsRoute,
    ) {
        ChallengeDetailsRoute(
            onBackClick = onBackClick,
            viewModel = koinNavViewModel(),
        )
    }
}

private fun String.makeSafeForNavigation(): String {
    return this.replace("/", "").replace("\\", "")
}
