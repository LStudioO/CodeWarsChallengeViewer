@file:OptIn(KoinInternalApi::class)

package com.vstorchevyi.codewars.di.scope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.koin.compose.LocalKoinApplication
import org.koin.compose.LocalKoinScope
import org.koin.core.annotation.KoinInternalApi
import org.koin.mp.KoinPlatformTools

/**
 * This is a workaround [org.koin.core.error.ClosedScopeException]: Scope '_root_' is closed
 *
 * See [issue](https://github.com/InsertKoinIO/koin/issues/1557).
 */
@Composable
fun KoinLocalScope(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalKoinScope provides KoinPlatformTools.defaultContext().get().scopeRegistry.rootScope,
        LocalKoinApplication provides KoinPlatformTools.defaultContext().get(),
        content = content,
    )
}
