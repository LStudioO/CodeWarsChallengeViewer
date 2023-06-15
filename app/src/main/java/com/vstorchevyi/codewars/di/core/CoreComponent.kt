package com.vstorchevyi.codewars.di.core

import org.koin.dsl.module

fun coreComponent() = module {
    includes(coreModule())
}
