package com.vstorchevyi.codewars.di

import com.vstorchevyi.codewars.di.core.coreComponent
import com.vstorchevyi.codewars.di.feature.featureComponent
import org.koin.dsl.module

fun appComponent() = module {
    includes(coreComponent())
    includes(featureComponent())
}
