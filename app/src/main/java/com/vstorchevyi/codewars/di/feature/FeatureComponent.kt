package com.vstorchevyi.codewars.di.feature

import com.vstorchevyi.codewars.di.feature.completed_challendge.challengeDetailsFeatureModule
import com.vstorchevyi.codewars.di.feature.user.userFeatureModule
import org.koin.dsl.module

fun featureComponent() = module {
    includes(userFeatureModule())
    includes(challengeDetailsFeatureModule())
}
