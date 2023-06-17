package com.core.utils.platform.network

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isAvailable: Flow<Boolean>
}
