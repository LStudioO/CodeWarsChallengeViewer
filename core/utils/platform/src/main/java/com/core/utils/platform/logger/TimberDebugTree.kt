package com.core.utils.platform.logger

import timber.log.Timber

// Disable Timber message formatting, because it can cause an exception
// https://proandroiddev.com/be-careful-what-you-log-it-could-crash-your-app-5fc67a44c842
class TimberDebugTree : Timber.DebugTree() {
    override fun formatMessage(message: String, args: Array<out Any?>): String {
        return message
    }
}
