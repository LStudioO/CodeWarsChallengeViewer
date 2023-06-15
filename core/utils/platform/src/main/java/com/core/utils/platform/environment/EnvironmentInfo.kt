package com.core.utils.platform.environment

interface EnvironmentInfo {
    val hostUrl: String
    fun isDebugBuildType(): Boolean
}
