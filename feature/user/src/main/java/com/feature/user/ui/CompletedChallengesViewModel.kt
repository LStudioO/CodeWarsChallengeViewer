package com.feature.user.ui

import androidx.lifecycle.ViewModel
import com.feature.user.domain.impl.GetUserCompletedChallengesUseCase
import com.feature.user.domain.model.CompletedChallenges
import kotlinx.coroutines.flow.flow

internal class CompletedChallengesViewModel(
    private val getCompletedChallenges: GetUserCompletedChallengesUseCase,
) : ViewModel() {

    val challenges = flow {
        emit(getCompletedChallenges().orNull() ?: CompletedChallenges.emptyInstance())
    }
}
