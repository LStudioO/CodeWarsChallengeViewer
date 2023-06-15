package com.vstorchevyi.codewars.data

import com.core.utils.platform.environment.EnvironmentInfo
import com.vstorchevyi.codewars.BuildConfig

class AppEnvironmentInfo : EnvironmentInfo {
    override val hostUrl: String
        get() = "https://www.codewars.com/api/v1/"

    override fun isDebugBuildType(): Boolean {
        return BuildConfig.DEBUG
    }
}
