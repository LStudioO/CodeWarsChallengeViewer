package com.feature.user.ui.challenge

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.feature.user.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CompletedChallengesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun toolbar_exists() {
        // Arrange
        val items = listOf<CompletedChallengeUiModel>()
        val pagingData = PagingData.from(items)
        val fakeDataFlow = MutableStateFlow(pagingData)

        // Act
        composeTestRule.setContent {
            val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()
            CompletedChallengesScreen(
                onChallengeClick = { _, _ -> },
                lazyPagingItems = lazyPagingItems,
            )
        }


        // Assert
        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.getString(
                    R.string.completed_challenges_toolbar_content_description,
                ),
            )
            .assertExists()
    }

    @Test
    fun swipeToRefresh_exists() {
        // Arrange
        val items = listOf<CompletedChallengeUiModel>()
        val pagingData = PagingData.from(items)
        val fakeDataFlow = MutableStateFlow(pagingData)

        // Act
        composeTestRule.setContent {
            val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()
            CompletedChallengesScreen(
                onChallengeClick = { _, _ -> },
                lazyPagingItems = lazyPagingItems,
            )
        }

        // Assert
        composeTestRule
            .onNodeWithTag(
                "swipeToRefresh",
            )
            .assertExists()
    }

    @Test
    fun retry_whenErrorWhileLoadingOccurs_isDisplayed() {
        // Arrange
        val pagingData = PagingData.from(
            data = emptyList<CompletedChallengeUiModel>(),
            sourceLoadStates = LoadStates(
                refresh = LoadState.Error(Exception()),
                prepend = LoadState.NotLoading(endOfPaginationReached = false),
                append = LoadState.NotLoading(endOfPaginationReached = false),
            ),
        )
        val fakeDataFlow = MutableStateFlow(pagingData)

        // Act
        composeTestRule.setContent {
            val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()
            CompletedChallengesScreen(
                onChallengeClick = { _, _ -> },
                lazyPagingItems = lazyPagingItems,
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.completed_challenges_loading_error,
                ),
            ).assertIsDisplayed()
    }

    @Test
    fun screenLoader_whenStartLoading_isDisplayed() {
        // Arrange
        val pagingData = PagingData.from(
            data = emptyList<CompletedChallengeUiModel>(),
            sourceLoadStates = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(endOfPaginationReached = false),
                append = LoadState.NotLoading(endOfPaginationReached = false),
            ),
        )
        val fakeDataFlow = MutableStateFlow(pagingData)

        // Act
        composeTestRule.setContent {
            val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()
            CompletedChallengesScreen(
                onChallengeClick = { _, _ -> },
                lazyPagingItems = lazyPagingItems,
            )
        }

        // Assert
        composeTestRule
            .onNodeWithTag(
                "pageLoader",
            )
            .assertIsDisplayed()
    }

    @Test
    fun challenges_whenLoaded_areDisplayed() {
        // Arrange
        val items = mutableListOf<CompletedChallengeUiModel>().apply {
            repeat(20) { index ->
                add(createChallengeUiModel(id = index.toString()))
            }
        }
        val pagingData = PagingData.from(
            data = items,
            sourceLoadStates = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true),
            ),
        )
        val fakeDataFlow = MutableStateFlow(pagingData)

        // Act
        composeTestRule.setContent {
            val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()
            CompletedChallengesScreen(
                onChallengeClick = { _, _ -> },
                lazyPagingItems = lazyPagingItems,
            )
        }

        // Assert
        items.forEachIndexed { index, item ->
            composeTestRule
                .onNodeWithTag(
                    "lazyContainer",
                )
                .performScrollToIndex(index + 1)

            composeTestRule
                .onNodeWithTag(
                    "challenge_${item.id}",
                )
                .assertExists()
        }
    }

    @Test
    fun challengeCard_whenShown_isClickable() {
        // Arrange
        val items = listOf(createChallengeUiModel(id = "1"))
        val pagingData = PagingData.from(
            data = items,
            sourceLoadStates = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true),
            ),
        )
        val fakeDataFlow = MutableStateFlow(pagingData)

        // Act
        composeTestRule.setContent {
            val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()
            CompletedChallengesScreen(
                onChallengeClick = { _, _ -> },
                lazyPagingItems = lazyPagingItems,
            )
        }

        // Assert
        composeTestRule
            .onNodeWithTag(
                "challenge_1_card",
            )
            .assertHasClickAction()
    }

    @Test
    fun pageRetry_whenLoadingFails_isDisplayed() {
        // Arrange
        val items = listOf(createChallengeUiModel())
        val pagingData = PagingData.from(
            data = items,
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.Error(Exception()),
            ),
        )
        val fakeDataFlow = MutableStateFlow(pagingData)

        // Act
        composeTestRule.setContent {
            val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()
            CompletedChallengesScreen(
                onChallengeClick = { _, _ -> },
                lazyPagingItems = lazyPagingItems,
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.completed_challenges_page_loading_error,
                ),
            ).assertIsDisplayed()
    }

    @Test
    fun pageRetry_whenShown_isClickable() {
        // Arrange
        val items = listOf(createChallengeUiModel())
        val pagingData = PagingData.from(
            data = items,
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.Error(Exception()),
            ),
        )
        val fakeDataFlow = MutableStateFlow(pagingData)

        // Act
        composeTestRule.setContent {
            val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()
            CompletedChallengesScreen(
                onChallengeClick = { _, _ -> },
                lazyPagingItems = lazyPagingItems,
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.completed_challenges_page_loading_retry,
                ),
            ).assertHasClickAction()
    }

    private fun createChallengeUiModel(
        id: String = "",
        name: String = "",
        completedLanguages: ImmutableList<String> = persistentListOf(),
        completedAt: String = "",
    ): CompletedChallengeUiModel {
        return CompletedChallengeUiModel(
            id = id,
            name = name,
            completedLanguages = completedLanguages,
            completedAt = completedAt,
        )
    }
}
