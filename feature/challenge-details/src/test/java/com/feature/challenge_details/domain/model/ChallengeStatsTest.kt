package com.feature.challenge_details.domain.model

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class ChallengeStatsTest(private val params: Params) {
    data class Params(val attempts: Int, val completed: Int, val expected: Int)

    @Test
    fun `success percent`() {
        // Arrange
        val sut = ChallengeStats(
            totalAttempts = params.attempts,
            totalCompleted = params.completed,
            totalStars = 0,
            voteScore = 0,
            unresolvedSuggestions = 0,
            unresolvedIssues = 0,
        )

        // Act
        val rate = sut.successPercent

        // Assert
        assertEquals(rate, params.expected)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun parameters() = arrayOf(
            Params(0, 0, 0),
            Params(100, 50, 50),
            Params(50, 100, 100),
            Params(-1, 100, 0),
            Params(100, -1, 0),
        )
    }
}
