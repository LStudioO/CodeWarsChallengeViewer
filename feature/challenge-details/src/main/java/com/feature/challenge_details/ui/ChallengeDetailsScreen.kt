@file:OptIn(ExperimentalLayoutApi::class)

package com.feature.challenge_details.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FactCheck
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.ui.component.*
import com.core.ui.icons.CwIcons
import com.core.ui.preview.ThemePreviews
import com.core.ui.theme.CwTheme
import com.core.ui.values.EdgePadding
import com.core.utils.logger.AppLogger
import com.feature.challenge_details.R
import com.feature.challenge_details.domain.model.ChallengeStats
import com.feature.challenge_details.ui.component.*
import com.feature.challenge_details.ui.model.*
import com.feature.challenge_details.ui.model.ActionByUiModel
import com.feature.challenge_details.ui.model.ChallengeDetailsError
import com.feature.challenge_details.ui.model.ChallengeDetailsScreenUiModel
import com.feature.challenge_details.ui.model.ChallengeDetailsUiModel
import com.feature.challenge_details.ui.model.RankUiModel
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.material3.Material3RichText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.getViewModel
import org.koin.compose.koinInject

@Composable
internal fun ChallengeDetailsRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: ChallengeDetailsViewModel = getViewModel(),
    logger: AppLogger = koinInject(),
) {
    val state = viewModel.screenFlow.collectAsStateWithLifecycle().value
    val uriHandler = LocalUriHandler.current

    ChallengeDetailsScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        state = state,
        onRetryClick = viewModel::onRetryClick,
        onLinkClick = { link ->
            try {
                uriHandler.openUri(link)
            } catch (e: Exception) {
                // No activity found to handle
                logger.e(e)
            }
        },
    )
}

@Composable
internal fun ChallengeDetailsScreen(
    modifier: Modifier = Modifier,
    state: ChallengeDetailsScreenUiModel,
    onBackClick: () -> Unit = {},
    onRetryClick: () -> Unit = {},
    onLinkClick: (String) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Toolbar(
                challengeName = state.challengeName,
                onBackClick = onBackClick,
            )
            if (state.challengeDetails != null) {
                val details = state.challengeDetails
                Column(
                    modifier = Modifier
                        .padding(horizontal = EdgePadding)
                        .verticalScroll(scrollState),
                ) {
                    FlowRow {
                        Rank(
                            rank = details.rank,
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        details.tags.forEachIndexed { index, tag ->
                            Tag(
                                value = tag,
                            )
                            if (index != details.tags.lastIndex) {
                                Spacer(modifier = Modifier.size(8.dp))
                            }
                        }
                    }

                    Category(value = details.category)

                    Spacer(modifier = Modifier.size(4.dp))

                    if (details.createdBy != null) {
                        val description = stringResource(
                            id = R.string.challenge_details_created_by_content_description,
                        )
                        val data = details.createdBy
                        ActionBy(
                            modifier = Modifier.semantics {
                                contentDescription = description
                            },
                            name = data.name,
                            date = data.date,
                            icon = Icons.Rounded.Psychology,
                            onClick = {
                                if (!data.url.isNullOrEmpty()) {
                                    onLinkClick.invoke(data.url)
                                }
                            },
                        )
                    }

                    Spacer(modifier = Modifier.size(4.dp))

                    if (details.approvedBy != null) {
                        val description = stringResource(
                            id = R.string.challenge_details_approved_by_content_description,
                        )
                        val data = details.approvedBy
                        ActionBy(
                            modifier = Modifier.semantics {
                                contentDescription = description
                            },
                            name = data.name,
                            date = data.date,
                            icon = Icons.Rounded.FactCheck,
                            onClick = {
                                if (!data.url.isNullOrEmpty()) {
                                    onLinkClick.invoke(data.url)
                                }
                            },
                        )
                    }

                    Spacer(modifier = Modifier.size(4.dp))

                    Description(
                        modifier = Modifier.testTag("description"),
                        markdown = state.challengeDetails.description,
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Languages(
                        modifier = Modifier.fillMaxWidth(),
                        languages = details.languages,
                    )

                    Statistic(
                        modifier = Modifier.fillMaxWidth(),
                        stats = details.stats,
                    )
                }
            }
        }
        when (state.error) {
            ChallengeDetailsError.NetworkError -> {
                TryAgainSection(
                    onRetryClick = onRetryClick,
                )
            }

            ChallengeDetailsError.NotFound -> {
                NotFoundErrorMessage()
            }

            ChallengeDetailsError.None -> {}
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                val description = stringResource(
                    id = R.string.challenge_details_loader_content_description,
                )
                CwProgressBar(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .semantics {
                            contentDescription = description
                        },
                )
            }
        }
    }
}

@Composable
private fun Rank(
    modifier: Modifier = Modifier,
    rank: RankUiModel?,
) {
    if (rank != null) {
        CwRank(
            modifier = modifier,
            value = rank.name,
            color = rank.color,
        )
    }
}

@Composable
private fun Languages(
    modifier: Modifier = Modifier,
    languages: ImmutableList<String>,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.challenge_details_languages_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            languages.forEach { language ->
                key("language_$language") {
                    Language(
                        modifier = Modifier.testTag("language"),
                        language = language,
                    )
                }
            }
        }
    }
}

@Composable
private fun Statistic(
    modifier: Modifier = Modifier,
    stats: ChallengeStats,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.challenge_details_stats_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.size(4.dp))

        StaggeredVerticalGrid {
            BasicStatisticCard(
                modifier = modifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.challenge_details_statistic_total_stars),
                value = stats.totalStars.toString(),
            )

            BasicStatisticCard(
                modifier = modifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.challenge_details_statistic_total_attempts),
                value = stats.totalAttempts.toString(),
            )

            BasicStatisticCard(
                modifier = modifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.challenge_details_statistic_total_completed),
                value = stats.totalCompleted.toString(),
            )

            SuccessRatioCard(
                modifier = modifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.challenge_details_statistic_success_rate),
                percentValue = stats.successPercent,
            )

            BasicStatisticCard(
                modifier = modifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.challenge_details_statistic_vote_score),
                value = stats.voteScore.toString(),
            )

            BasicStatisticCard(
                modifier = modifier
                    .fillMaxWidth(),
                title = stringResource(id = R.string.challenge_details_statistic_unresolved_issues),
                value = stats.unresolvedIssues.toString(),
            )

            BasicStatisticCard(
                modifier = modifier
                    .fillMaxWidth(),
                title = stringResource(
                    id = R.string.challenge_details_statistic_unresolved_suggestions,
                ),
                value = stats.unresolvedIssues.toString(),
            )
        }
    }
}

@Composable
private fun TryAgainSection(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = EdgePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.challenge_details_loading_error),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.size(4.dp))

        OutlinedButton(onClick = onRetryClick) {
            Text(
                text = stringResource(id = com.core.ui.R.string.retry_button),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun NotFoundErrorMessage(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = EdgePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.challenge_details_not_found_error),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
private fun Toolbar(
    modifier: Modifier = Modifier,
    challengeName: String,
    onBackClick: () -> Unit,
) {
    val description = stringResource(
        id = R.string.challenge_details_toolbar_content_description,
    )
    CwTopAppBar(
        modifier = modifier.semantics {
            contentDescription = description
        },
        title = challengeName,
        navigationIcon = CwIcons.ArrowBack,
        navigationIconContentDescription = stringResource(
            id = com.core.ui.R.string.back_button_description,
        ),
        onNavigationClick = onBackClick,
    )
}

@Composable
fun Description(
    modifier: Modifier = Modifier,
    markdown: String,
) {
    Material3RichText(
        modifier = modifier,
    ) {
        Markdown(
            content = markdown,
        )
    }
}

@ThemePreviews
@Composable
private fun ChallengeDetailsSuccessPreview() {
    CwTheme {
        CwBackground {
            ChallengeDetailsScreen(
                state = ChallengeDetailsScreenUiModel(
                    isLoading = false,
                    error = ChallengeDetailsError.None,
                    challengeDetails = ChallengeDetailsUiModel(
                        id = "1",
                        name = "Find all possible number combos that sum to a number",
                        description = "Aspect-oriented programming (`AOP`) is programming that adds additional behavior (`advice`) to existing functionality without actually modifying that functionality.\n\nCreate a method called `adviseBefore`.  This method will take two arguments, a function (`target`) and the advising function (`advice`).\n\n`adviseBefore` should return a function that, when executed, will first execute the advising function and then the original method with the following conditions:\n\n* The arguments passed to the function that `adviseBefore` returns should be passed to the advising function.\n* If the advising function returns an array, the array should replace the arguments passed to the original method.\n* If the advising function does not return an array, the original arguments should be passed to the original method.\n* The return value of the original method should be returned.",
                        rank = RankUiModel(
                            name = "8 kyu",
                            color = Color.Red,
                        ),
                        createdBy = ActionByUiModel(
                            name = "GaurangTandon",
                            url = "https://www.codewars.com/users/GaurangTandon",
                            date = "2017-08-10",
                        ),
                        approvedBy = ActionByUiModel(
                            name = "Voile",
                            url = "https://www.codewars.com/users/Voile",
                            date = "2012-05-03",
                        ),
                        category = "Algorithms",
                        languages = persistentListOf("javascript", "kotlin"),
                        tags = persistentListOf("Fundamentals", "Programming"),
                        stats = ChallengeStats(
                            totalAttempts = 200,
                            totalCompleted = 158,
                            totalStars = 38,
                            voteScore = 123000,
                            unresolvedSuggestions = 2,
                            unresolvedIssues = 1,
                        ),
                    ),
                    challengeName = "Challenge Name",
                ),
                onBackClick = {},
                onRetryClick = {},
                onLinkClick = {},
            )
        }
    }
}

@ThemePreviews
@Composable
private fun ChallengeDetailsNetworkErrorPreview() {
    CwTheme {
        CwBackground {
            ChallengeDetailsScreen(
                state = ChallengeDetailsScreenUiModel(
                    isLoading = false,
                    error = ChallengeDetailsError.NetworkError,
                    challengeDetails = null,
                    challengeName = "Challenge Name",
                ),
                onBackClick = {},
                onRetryClick = {},
                onLinkClick = {},
            )
        }
    }
}

@ThemePreviews
@Composable
private fun ChallengeDetailsNotFoundErrorPreview() {
    CwTheme {
        CwBackground {
            ChallengeDetailsScreen(
                state = ChallengeDetailsScreenUiModel(
                    isLoading = false,
                    error = ChallengeDetailsError.NotFound,
                    challengeDetails = null,
                    challengeName = "Challenge Name",
                ),
                onBackClick = {},
                onRetryClick = {},
                onLinkClick = {},
            )
        }
    }
}

@ThemePreviews
@Composable
private fun ChallengeDetailsLoadingPreview() {
    CwTheme {
        CwBackground {
            ChallengeDetailsScreen(
                state = ChallengeDetailsScreenUiModel(
                    isLoading = true,
                    error = ChallengeDetailsError.None,
                    challengeDetails = null,
                    challengeName = "Challenge Name",
                ),
                onBackClick = {},
                onRetryClick = {},
                onLinkClick = {},
            )
        }
    }
}
