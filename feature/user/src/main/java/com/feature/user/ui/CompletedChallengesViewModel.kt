package com.feature.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.feature.user.domain.impl.GetUserCompletedChallengesUseCase

internal class CompletedChallengesViewModel(
    getCompletedChallenges: GetUserCompletedChallengesUseCase,
) : ViewModel() {

    val challenges = getCompletedChallenges().cachedIn(viewModelScope)
}
