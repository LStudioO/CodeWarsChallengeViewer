@file:OptIn(ExperimentalComposeUiApi::class)

package com.vstorchevyi.codewars

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.core.ui.component.CwBackground
import com.core.utils.platform.network.NetworkMonitor
import com.feature.user.ui.challenge.completedChallengesRoute
import com.vstorchevyi.codewars.navigation.RootNavGraph
import org.koin.compose.koinInject

@Composable
fun MainApp(
    networkMonitor: NetworkMonitor = koinInject(),
) {
    CwBackground {
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }

        val isOnline by networkMonitor.isAvailable.collectAsStateWithLifecycle(initialValue = true)

        val notConnectedMessage = stringResource(R.string.connectivity_not_connected)
        LaunchedEffect(!isOnline) {
            if (!isOnline) {
                snackbarHostState.showSnackbar(
                    message = notConnectedMessage,
                    duration = SnackbarDuration.Indefinite,
                )
            }
        }

        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            contentWindowInsets = WindowInsets(0.dp),
            snackbarHost = {
                SnackbarHost(
                    modifier = Modifier.navigationBarsPadding(),
                    hostState = snackbarHostState,
                )
            },
            content = { innerPadding ->
                RootNavGraph(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .padding(innerPadding),
                    navController = navController,
                    startDestination = completedChallengesRoute,
                )
            },
        )
    }
}
