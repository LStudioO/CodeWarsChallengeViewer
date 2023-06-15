package com.feature.user.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.core.ui.theme.CodeWarsChallengeViewerTheme
import com.feature.user.domain.model.CompletedChallenge
import org.koin.androidx.compose.getViewModel

@Composable
internal fun CompletedChallengesRoute(
    modifier: Modifier = Modifier,
    onChallengeClick: (String) -> Unit,
    viewModel: CompletedChallengesViewModel = getViewModel(),
) {
    val state = viewModel.challenges.collectAsLazyPagingItems()

    CompletedChallengesScreen(
        modifier = modifier,
        onChallengeClick = onChallengeClick,
        challenges = state,
    )
}

@Composable
internal fun CompletedChallengesScreen(
    modifier: Modifier = Modifier,
    onChallengeClick: (String) -> Unit,
    challenges: LazyPagingItems<CompletedChallenge>,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(count = challenges.itemCount) { index ->
            val challenge = challenges[index]
            ChallengeElement(challenge = challenge) {
                if (challenge?.id != null) {
                    challenges.refresh()
                    onChallengeClick.invoke(challenge.id)
                } else {
                    // Show an error message
                }
            }
        }

        when (val state = challenges.loadState.refresh) {
            is LoadState.Error -> {
                item {
                    Text(text = "Error: ${state.error.message}")
                }
            }

            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refresh loading",
                        )

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }

            else -> {}
        }

        when (val state = challenges.loadState.append) {
            is LoadState.Error -> {
                item {
                    Text(text = "Pagination error: ${state.error.message}")
                }
            }

            is LoadState.Loading -> {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Pagination loading")

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun ChallengeElement(
    challenge: CompletedChallenge?,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick.invoke() },
    ) {
        if (challenge != null) {
            Text(text = challenge.name)
        } else {
            Text(text = "Placeholder", color = Color.Green)
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
