package com.feature.user.ui.challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.feature.user.domain.impl.GetUserCompletedChallengesUseCase
import com.feature.user.ui.challenge.mapper.CompletedChallengeMapper
import kotlinx.coroutines.flow.map

internal class CompletedChallengesViewModel(
    getCompletedChallenges: GetUserCompletedChallengesUseCase,
    mapper: CompletedChallengeMapper,
) : ViewModel() {

    val challenges = getCompletedChallenges()
        .map { pagingData ->
            pagingData.map(mapper::toUi)
        }
        .cachedIn(viewModelScope)
}
