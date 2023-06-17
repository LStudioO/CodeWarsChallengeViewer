package com.vstorchevyi.codewars

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.core.utils.platform.network.NetworkMonitor
import com.feature.user.ui.completedChallengesRoute
import com.vstorchevyi.codewars.navigation.RootNavGraph
import org.koin.compose.koinInject

@Composable
fun MainApp(
    networkMonitor: NetworkMonitor = koinInject(),
) {
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->

            RootNavGraph(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = completedChallengesRoute,
            )
        },
    )
}
