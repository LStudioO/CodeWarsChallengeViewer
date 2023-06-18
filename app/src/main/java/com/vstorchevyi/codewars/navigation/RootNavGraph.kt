package com.vstorchevyi.codewars.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.core.ui.component.CwTopAppBar
import com.core.ui.icons.CwIcons
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
            onChallengeClick = {
                navController.navigate("test_details")
            },
        )

        // A temporary stub for the details feature
        composable(route = "test_details") {
            Column {
                CwTopAppBar(
                    title = "Test details",
                    navigationIcon = CwIcons.ArrowBack,
                    navigationIconContentDescription = stringResource(
                        id = com.core.ui.R.string.back_button_description,
                    ),
                    onNavigationClick = navigateBack,
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Stub details",
                    )
                }
            }
        }
    }
}
