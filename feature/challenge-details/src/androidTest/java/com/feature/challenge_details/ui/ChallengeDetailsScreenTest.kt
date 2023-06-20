package com.feature.challenge_details.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.feature.challenge_details.R
import com.feature.challenge_details.domain.model.ChallengeStats
import com.feature.challenge_details.ui.model.*
import com.feature.challenge_details.ui.model.ActionByUiModel
import com.feature.challenge_details.ui.model.ChallengeDetailsError
import com.feature.challenge_details.ui.model.ChallengeDetailsScreenUiModel
import com.feature.challenge_details.ui.model.ChallengeDetailsUiModel
import com.feature.challenge_details.ui.model.RankUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ChallengeDetailsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun toolbar_exists() {
        // Arrange
        val state = createScreenUiModel()

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }


        // Assert
        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.getString(
                    R.string.challenge_details_toolbar_content_description,
                ),
            )
            .assertExists()
    }

    @Test
    fun toolbar_title_isChallengeName() {
        // Arrange
        val state = createScreenUiModel(
            challengeName = "Name",
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }


        // Assert
        composeTestRule
            .onNodeWithText("Name")
            .assertExists()
    }

    @Test
    fun description_whenLoaded_exists() {
        // Arrange
        val state = createScreenUiModel(
            challengeDetails = createDetailsUiModel(
                description = "Challenge description",
            ),
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }


        // Assert
        composeTestRule
            .onNodeWithText("Challenge description")
            .assertExists()
    }

    @Test
    fun category_whenLoaded_exists() {
        // Arrange
        val state = createScreenUiModel(
            challengeDetails = createDetailsUiModel(
                category = "Category",
            ),
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }


        // Assert
        composeTestRule
            .onNodeWithText("Category")
            .assertExists()
    }

    @Test
    fun createdBy_whenNotNull_exists() {
        // Arrange
        val state = createScreenUiModel(
            challengeDetails = createDetailsUiModel(
                createdBy = ActionByUiModel(
                    name = "test_user",
                    url = "url",
                    date = null,
                ),
            ),
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }


        // Assert
        composeTestRule
            .onNodeWithText("test_user")
            .assertExists()
    }

    @Test
    fun createdBy_whenLoaded_isClickable() {
        // Arrange
        val state = createScreenUiModel(
            challengeDetails = createDetailsUiModel(
                createdBy = ActionByUiModel(
                    name = "test_user",
                    url = "url",
                    date = null,
                ),
            ),
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }


        // Assert
        composeTestRule
            .onNodeWithText("test_user")
            .assertHasClickAction()
    }

    @Test
    fun approvedBy_whenNotNull_exists() {
        // Arrange
        val state = createScreenUiModel(
            challengeDetails = createDetailsUiModel(
                approvedBy = ActionByUiModel(
                    name = "test_user",
                    url = "url",
                    date = null,
                ),
            ),
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }


        // Assert
        composeTestRule
            .onNodeWithText("test_user")
            .assertExists()
    }

    @Test
    fun approvedBy_whenLoaded_isClickable() {
        // Arrange
        val state = createScreenUiModel(
            challengeDetails = createDetailsUiModel(
                approvedBy = ActionByUiModel(
                    name = "test_user",
                    url = "url",
                    date = null,
                ),
            ),
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }


        // Assert
        composeTestRule
            .onNodeWithText("test_user")
            .assertHasClickAction()
    }

    @Test
    fun languages_whenLoaded_exist() {
        // Arrange
        val state = createScreenUiModel(
            challengeDetails = createDetailsUiModel(
                languages = persistentListOf("1", "2", "3"),
            ),
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }


        // Assert
        composeTestRule
            .onAllNodesWithTag("language").assertCountEquals(3)
    }

    @Test
    fun statistics_whenLoaded_exist() {
        // Arrange
        val state = createScreenUiModel(
            challengeDetails = createDetailsUiModel(
                stats = ChallengeStats(
                    totalStars = 0,
                    totalCompleted = 0,
                    totalAttempts = 0,
                    voteScore = 0,
                    unresolvedIssues = 0,
                    unresolvedSuggestions = 0,
                ),
            ),
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }

        // Assert
        composeTestRule.apply {
            // Success rate
            onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.challenge_details_statistic_success_rate,
                ),
            ).assertExists()

            // Total attempts
            onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.challenge_details_statistic_total_attempts,
                ),
            ).assertExists()

            // Total completed
            onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.challenge_details_statistic_total_completed,
                ),
            ).assertExists()

            // Total stars
            onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.challenge_details_statistic_total_stars,
                ),
            ).assertExists()

            // Vote score
            onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.challenge_details_statistic_vote_score,
                ),
            ).assertExists()

            // Unresolved issues
            onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.challenge_details_statistic_unresolved_issues,
                ),
            ).assertExists()

            // Unresolved suggestions
            onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.challenge_details_statistic_unresolved_suggestions,
                ),
            ).assertExists()
        }
    }

    @Test
    fun screenLoader_whenStartLoading_isDisplayed() {
        // Arrange
        val state = createScreenUiModel(
            isLoading = true,
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }

        // Assert
        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.getString(
                    R.string.challenge_details_loader_content_description,
                ),
            )
            .assertIsDisplayed()
    }

    @Test
    fun retry_whenErrorWhileLoadingOccurs_isDisplayed() {
        // Arrange
        val state = createScreenUiModel(
            error = ChallengeDetailsError.NetworkError,
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.challenge_details_loading_error,
                ),
            ).assertIsDisplayed()
    }

    @Test
    fun error_whileLoadingOccurs_isNotDisplayed() {
        // Arrange
        val state = createScreenUiModel(
            error = ChallengeDetailsError.NetworkError,
            isLoading = true,
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(
                    R.string.challenge_details_loading_error,
                ),
            ).assertDoesNotExist()
    }

    @Test
    fun retry_whenShown_isClickable() {
        // Arrange
        val state = createScreenUiModel(
            error = ChallengeDetailsError.NetworkError,
        )

        // Act
        composeTestRule.setContent {
            ChallengeDetailsScreen(
                state = state,
            )
        }

        // Assert
        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(
                    com.core.ui.R.string.retry_button,
                ),
            ).assertHasClickAction()
    }

    private fun createScreenUiModel(
        isLoading: Boolean = false,
        error: ChallengeDetailsError = ChallengeDetailsError.None,
        challengeDetails: ChallengeDetailsUiModel? = null,
        challengeName: String = "",
    ): ChallengeDetailsScreenUiModel {
        return ChallengeDetailsScreenUiModel(
            isLoading = isLoading,
            error = error,
            challengeDetails = challengeDetails,
            challengeName = challengeName,
        )
    }

    private fun createDetailsUiModel(
        id: String = "",
        name: String = "",
        description: String = "",
        category: String = "",
        rank: RankUiModel? = null,
        createdBy: ActionByUiModel? = null,
        approvedBy: ActionByUiModel? = null,
        languages: ImmutableList<String> = persistentListOf(),
        tags: ImmutableList<String> = persistentListOf(),
        stats: ChallengeStats = ChallengeStats(
            totalStars = 0,
            totalCompleted = 0,
            totalAttempts = 0,
            voteScore = 0,
            unresolvedIssues = 0,
            unresolvedSuggestions = 0,
        ),
    ): ChallengeDetailsUiModel {
        return ChallengeDetailsUiModel(
            id = id,
            name = name,
            description = description,
            category = category,
            rank = rank,
            createdBy = createdBy,
            approvedBy = approvedBy,
            languages = languages,
            tags = tags,
            stats = stats,
        )
    }
}
