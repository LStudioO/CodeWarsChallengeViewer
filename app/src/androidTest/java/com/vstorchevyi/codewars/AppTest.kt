package com.vstorchevyi.codewars

import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.core.utils.platform.environment.EnvironmentInfo
import com.core.utils.platform.network.NetworkMonitor
import com.feature.user.data.local.data_source.UserDataSource
import com.feature.user.domain.model.User
import com.vstorchevyi.codewars.di.KoinTestRule
import com.vstorchevyi.codewars.di.appComponent
import com.vstorchevyi.codewars.server.MockServerTestRule
import com.vstorchevyi.codewars.server.SuccessDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import kotlin.properties.ReadOnlyProperty

private const val PORT = 8080

@OptIn(ExperimentalTestApi::class)
class AppTest {
    private val instrumentedTestModule = module {
        factory<UserDataSource> {
            object : UserDataSource {
                override val user: User
                    get() = User("testUser")
            }
        }
        factory<EnvironmentInfo> {
            object : EnvironmentInfo {
                override val hostUrl: String
                    get() = "http://localhost:$PORT"

                override fun isDebugBuildType(): Boolean {
                    return true
                }

            }
        }
        factory<NetworkMonitor> {
            object : NetworkMonitor {
                override val isAvailable: Flow<Boolean>
                    get() = flowOf(false)
            }
        }
    }

    @get:Rule(order = 0)
    val koinTestRule = KoinTestRule(
        modules = listOf(appComponent(), instrumentedTestModule),
    )

    @get:Rule(order = 1)
    val serverRule = MockServerTestRule(
        port = PORT,
        responseDispatcher = SuccessDispatcher(
            userId = "testUser",
        ),
    )

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun AndroidComposeTestRule<*, *>.stringResource(@StringRes resId: Int) =
        ReadOnlyProperty<Any?, String> { _, _ -> activity.getString(resId) }

    // Matching strings
    private val completedChallenges by composeTestRule.stringResource(
        com.feature.user.R.string.completed_challenges_title,
    )
    private val completedChallengeCard by composeTestRule.stringResource(
        com.feature.user.R.string.completed_challenges_card_description,
    )
    private val connectionError by composeTestRule.stringResource(
        R.string.connectivity_not_connected,
    )
    private val backButton by composeTestRule.stringResource(
        com.core.ui.R.string.back_button_description,
    )
    private val challengeDetails by composeTestRule.stringResource(
        com.feature.challenge_details.R.string.challenge_details_toolbar_content_description,
    )
    private val challengeDetailsCreatedBy by composeTestRule.stringResource(
        com.feature.challenge_details.R.string.challenge_details_created_by_content_description,
    )
    private val challengeDetailsApprovedBy by composeTestRule.stringResource(
        com.feature.challenge_details.R.string.challenge_details_approved_by_content_description,
    )

    @Test
    fun rootScreen_isCompletedChallenges() {
        composeTestRule.apply {
            onNodeWithText(completedChallenges)
                .assertExists()
        }
    }

    @Test(expected = NoActivityResumedException::class)
    fun rootScreen_whenBack_quitsApp() {
        composeTestRule.apply {
            // the user uses the system button/gesture to go back
            Espresso.pressBack()
        }
    }

    @Test
    fun challenges_whenLoaded_areDisplayed() {
        composeTestRule.apply {
            waitUntilAtLeastOneExists(
                hasContentDescription(completedChallengeCard),
            )
        }
    }

    @Test
    fun challenges_whenClick_navigatesToDetails() {
        composeTestRule.apply {
            navigateToDetails()

            // Check the details screen is shown
            onNodeWithContentDescription(challengeDetails)
                .assertExists()
        }
    }

    @Test
    fun details_whenBack_returnsToCompletedChallenges() {
        composeTestRule.apply {
            navigateToDetails()

            // Go back
            Espresso.pressBack()

            // Check if the completed challenges screen is shown
            onNodeWithText(completedChallenges)
                .assertExists()
        }
    }

    @Test
    fun details_backButtonIsShown() {
        composeTestRule.apply {
            navigateToDetails()

            onNodeWithContentDescription(backButton).assertExists()
        }
    }

    @Test
    fun details_backButtonNavigatesToChallenges() {
        composeTestRule.apply {
            navigateToDetails()

            // Click on the back button
            onNodeWithContentDescription(backButton)
                .performClick()

            // Check the details screen is shown
            onNodeWithText(completedChallenges)
                .assertExists()
        }
    }

    @Test
    fun details_clickOnApprovedBy_navigatesToViewAction() {
        composeTestRule.apply {
            navigateToDetails()

            // Lister for intents
            Intents.init()
            // Click on the approvedBy node
            onNodeWithContentDescription(challengeDetailsApprovedBy)
                .performClick()

            // Assert an intent
            intended(
                allOf(
                    hasAction(Intent.ACTION_VIEW),
                ),
            )
            Intents.release()
        }
    }

    @Test
    fun details_clickOnCreatedBy_navigatesToViewAction() {
        composeTestRule.apply {
            navigateToDetails()

            // Lister for intents
            Intents.init()
            // Click on the approvedBy node
            onNodeWithContentDescription(challengeDetailsCreatedBy)
                .performClick()

            // Assert an intent
            intended(
                allOf(
                    hasAction(Intent.ACTION_VIEW),
                ),
            )
            Intents.release()
        }
    }

    @Test
    fun anyScreen_noAvailableConnection_messageIsShown() {
        composeTestRule.apply {
            // Check on the root screen
            onNodeWithText(connectionError).assertExists()

            navigateToDetails()

            // Check on the details screen
            onNodeWithText(connectionError).assertExists()
        }
    }

    private fun AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.navigateToDetails() {
        // Wait for challenges to load
        waitUntilAtLeastOneExists(
            hasContentDescription(completedChallengeCard),
        )

        // Click on a challenge
        onAllNodesWithContentDescription(completedChallengeCard)
            .onFirst()
            .performClick()
    }
}
