@file:OptIn(ExperimentalTestApi::class)

package com.vstorchevyi.codewars

import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoActivityResumedException
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.core.utils.platform.environment.EnvironmentInfo
import com.core.utils.platform.network.NetworkMonitor
import com.vstorchevyi.codewars.di.KoinTestRule
import com.vstorchevyi.codewars.di.appComponent
import com.vstorchevyi.codewars.server.MockServerTestRule
import com.vstorchevyi.codewars.server.SuccessDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import kotlin.properties.ReadOnlyProperty

private const val PORT = 8080

class AppTest {
    private val instrumentedTestModule = module {
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
        responseDispatcher = SuccessDispatcher,
    )

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun AndroidComposeTestRule<*, *>.stringResource(@StringRes resId: Int) =
        ReadOnlyProperty<Any?, String> { _, _ -> activity.getString(resId) }

    // Matching strings
    private val completedChallenges by composeTestRule.stringResource(
        com.feature.user.R.string.completed_challenges_title,
    )
    private val settings by composeTestRule.stringResource(
        com.feature.user.R.string.completed_challenges_toolbar_settings_description,
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

    private val challengeDetails = "Test details"

    @Test
    fun rootScreen_isCompletedChallenges() {
        composeTestRule.apply {
            onNodeWithText(completedChallenges)
                .assertExists()
        }
    }

    @Test
    fun rootScreen_showSettingsIcon() {
        composeTestRule.apply {
            onNodeWithContentDescription(settings).assertExists()
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
            onNodeWithText(challengeDetails)
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
    fun details_noSettingIconIsShown() {
        composeTestRule.apply {
            navigateToDetails()

            onNodeWithContentDescription(settings).assertDoesNotExist()
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
