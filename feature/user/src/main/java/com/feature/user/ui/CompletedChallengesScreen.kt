package com.feature.user.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.ui.theme.CodeWarsChallengeViewerTheme
import com.feature.user.domain.model.CompletedChallenges
import org.koin.androidx.compose.getViewModel

@Composable
internal fun CompletedChallengesRoute(
    modifier: Modifier = Modifier,
    onChallengeClick: (String) -> Unit,
    viewModel: CompletedChallengesViewModel = getViewModel(),
) {
    val state = viewModel.challenges.collectAsStateWithLifecycle(
        initialValue = CompletedChallenges.emptyInstance(),
    )
    CompletedChallengesScreen(
        modifier = modifier,
        onChallengeClick = onChallengeClick,
        state = state.value,
    )
}

@Composable
internal fun CompletedChallengesScreen(
    modifier: Modifier = Modifier,
    onChallengeClick: (String) -> Unit,
    state: CompletedChallenges,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        state.challenges.forEach {
            Text(
                modifier = Modifier.clickable {
                    onChallengeClick.invoke(it.id)
                },
                text = it.name,
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Preview
@Composable
internal fun CompletedChallengesPreview() {
    CodeWarsChallengeViewerTheme {
        // TODO: add the screen
    }
}
