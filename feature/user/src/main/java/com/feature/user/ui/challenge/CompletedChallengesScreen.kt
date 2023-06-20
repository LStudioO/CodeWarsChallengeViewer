@file:OptIn(ExperimentalFoundationApi::class)

package com.feature.user.ui.challenge

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.core.ui.component.CwBackground
import com.core.ui.component.CwTopAppBar
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme
import com.core.ui.values.EdgePadding
import com.feature.user.R
import com.feature.user.ui.challenge.component.CompletedChallengeCard
import com.feature.user.ui.challenge.component.providers.CompletedChallengeProvider
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.getViewModel

@Composable
internal fun CompletedChallengesRoute(
    modifier: Modifier = Modifier,
    onChallengeClick: (String, String) -> Unit,
    viewModel: CompletedChallengesViewModel = getViewModel(),
) {
    val state = viewModel.challenges.collectAsLazyPagingItems()

    CompletedChallengesScreen(
        modifier = modifier,
        onChallengeClick = onChallengeClick,
        lazyPagingItems = state,
    )
}

private enum class ListContentType {
    Header,
    Challenge,
    Placeholder,
    PageRetry
}

@Composable
internal fun CompletedChallengesScreen(
    modifier: Modifier = Modifier,
    onChallengeClick: (String, String) -> Unit,
    lazyPagingItems: LazyPagingItems<CompletedChallengeUiModel>,
) {
    val listState = rememberLazyListState()

    Box(
        modifier = modifier,
    ) {
        /**
        Material 3 doesn't support the pull-to-refresh feature, so the only option is to use
        the deprecated [SwipeRefresh]
         **/
        @Suppress("DEPRECATION")
        SwipeRefresh(
            modifier = Modifier.testTag("swipeToRefresh"),
            state = rememberSwipeRefreshState(
                lazyPagingItems.loadState.refresh is LoadState.Loading,
            ),
            onRefresh = { lazyPagingItems.refresh() },
            indicator = { indicatorState, trigger ->
                SwipeRefreshIndicator(
                    state = indicatorState,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    contentColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.background,
                )
            },
            indicatorPadding = PaddingValues(vertical = 40.dp),
        ) {
            LazyColumn(
                modifier = modifier.testTag("challengesContainer"),
                state = listState,
                contentPadding = PaddingValues(bottom = 8.dp),
            ) {

                toolbar()

                firstTimeChallengePlaceholders(
                    modifier = Modifier.padding(
                        horizontal = EdgePadding,
                        vertical = 8.dp,
                    ),
                    lazyPagingItems = lazyPagingItems,
                )

                completedChallenges(
                    modifier = Modifier.padding(
                        horizontal = EdgePadding,
                        vertical = 8.dp,
                    ),
                    lazyPagingItems = lazyPagingItems,
                    onChallengeClick = onChallengeClick,
                )

                pageRetryButton(
                    modifier = Modifier
                        .padding(
                            horizontal = EdgePadding,
                            vertical = 8.dp,
                        ),
                    lazyPagingItems = lazyPagingItems,
                )
            }
        }

        TryAgainSection(
            lazyPagingItems = lazyPagingItems,
        )
    }
}

private fun LazyListScope.toolbar(
    modifier: Modifier = Modifier,
) {
    stickyHeader(
        contentType = ListContentType.Header,
    ) {
        val description = stringResource(
            id = R.string.completed_challenges_toolbar_content_description,
        )
        CwTopAppBar(
            modifier = modifier.semantics {
                contentDescription = description
            },
            title = stringResource(id = R.string.completed_challenges_title),
        )
    }
}

private fun LazyListScope.pageRetryButton(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<CompletedChallengeUiModel>,
) {
    if (lazyPagingItems.loadState.append is LoadState.Error) {
        item(
            contentType = ListContentType.PageRetry,
        ) {
            RetryPageSection(
                modifier = modifier,
            ) {
                lazyPagingItems.retry()
            }
        }
    }
}

private fun LazyListScope.completedChallenges(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<CompletedChallengeUiModel>,
    onChallengeClick: (String, String) -> Unit,
) {
    if (lazyPagingItems.loadState.refresh !is LoadState.Error) {
        items(
            contentType = { index ->
                val challenge = lazyPagingItems[index]
                if (challenge != null) ListContentType.Challenge else ListContentType.Placeholder
            },
            key = { index ->
                val challenge = lazyPagingItems[index]
                challenge?.id ?: "placeholder_$index"
            },
            count = lazyPagingItems.itemCount,
        ) { index ->
            val challenge = lazyPagingItems[index]
            if (challenge != null) {
                val description = stringResource(
                    id = R.string.completed_challenges_card_description,
                )
                CompletedChallengeCard(
                    modifier = modifier
                        .testTag("challenge_${challenge.id}")
                        .semantics { contentDescription = description },
                    cardModifier = Modifier.testTag("challenge_${challenge.id}_card"),
                    orderNumber = index + 1,
                    name = challenge.name,
                    languages = challenge.completedLanguages,
                    completedAt = challenge.completedAt,
                    isLoading = false,
                    onClick = {
                        onChallengeClick.invoke(challenge.id, challenge.name)
                    },
                )
            } else if (lazyPagingItems.loadState.append !is LoadState.Error) {
                CompletedChallengeCard(
                    modifier = modifier,
                    orderNumber = index + 1,
                    name = "Test name",
                    languages = persistentListOf("javascript"),
                    completedAt = "2022-02-02",
                    isLoading = true,
                    onClick = { },
                )
            }
        }
    }
}

private fun LazyListScope.firstTimeChallengePlaceholders(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<CompletedChallengeUiModel>,
) {
    if (lazyPagingItems.loadState.refresh is LoadState.Loading && lazyPagingItems.itemCount == 0) {
        items(
            contentType = {
                ListContentType.Placeholder
            },
            key = { index ->
                "placeholder_$index"
            },
            count = 10,
        ) { index ->
            CompletedChallengeCard(
                modifier = modifier.testTag("placeholder"),
                orderNumber = index + 1,
                name = "Test name",
                languages = persistentListOf("javascript"),
                completedAt = "2022-02-02",
                isLoading = true,
                onClick = { },
            )
        }
    }
}

@Composable
private fun TryAgainSection(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<CompletedChallengeUiModel>,
) {
    if (lazyPagingItems.loadState.refresh is LoadState.Error) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = EdgePadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.completed_challenges_loading_error),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.size(4.dp))
            OutlinedButton(onClick = { lazyPagingItems.refresh() }) {
                Text(
                    text = stringResource(id = com.core.ui.R.string.retry_button),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun RetryPageSection(
    modifier: Modifier = Modifier,
    retry: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.completed_challenges_page_loading_error),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedButton(onClick = retry) {
            Text(
                text = stringResource(id = R.string.completed_challenges_page_loading_retry),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun CompletedChallengesSuccessPreview() {
    val items = CompletedChallengeProvider().values.toList()
    val pagingData = PagingData.from(items)
    val fakeDataFlow = MutableStateFlow(pagingData)
    val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()
    CwTheme {
        CwBackground {
            CompletedChallengesScreen(
                onChallengeClick = { _, _ -> },
                lazyPagingItems = lazyPagingItems,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun CompletedChallengesErrorPreview() {
    val pagingData = PagingData.from(
        data = emptyList<CompletedChallengeUiModel>(),
        sourceLoadStates = LoadStates(
            refresh = LoadState.Error(Exception()),
            prepend = LoadState.NotLoading(endOfPaginationReached = false),
            append = LoadState.NotLoading(endOfPaginationReached = false),
        ),
    )
    val fakeDataFlow = MutableStateFlow(pagingData)
    val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()
    CwTheme {
        CwBackground {
            CompletedChallengesScreen(
                onChallengeClick = { _, _ -> },
                lazyPagingItems = lazyPagingItems,
            )
        }
    }
}

@ThemePreviews
@Composable
private fun CompletedChallengesErrorPagePreview() {
    val items = CompletedChallengeProvider().values.toList()
    val pagingData = PagingData.from(
        data = items,
        sourceLoadStates = LoadStates(
            refresh = LoadState.NotLoading(endOfPaginationReached = false),
            prepend = LoadState.NotLoading(endOfPaginationReached = false),
            append = LoadState.Error(Exception()),
        ),
    )
    val fakeDataFlow = MutableStateFlow(pagingData)
    val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()
    CwTheme {
        CwBackground {
            CompletedChallengesScreen(
                onChallengeClick = { _, _ -> },
                lazyPagingItems = lazyPagingItems,
            )
        }
    }
}
