package com.core.utils.platform.logger

import com.core.utils.logger.AppLogger
import timber.log.Timber

class TimberLogger : AppLogger {
    override fun tag(tag: String) {
        Timber.tag(tag)
    }

    override fun v(message: String?) {
        Timber.v(message)
    }

    override fun v(t: Throwable?, message: String?) {
        Timber.v(t, message)
    }

    override fun v(t: Throwable?) {
        Timber.v(t)
    }

    override fun d(message: String?) {
        Timber.d(message)
    }

    override fun d(t: Throwable?, message: String?) {
        Timber.d(t, message)
    }

    override fun d(t: Throwable?) {
        Timber.d(t)
    }

    override fun i(message: String?) {
        Timber.i(message)
    }

    override fun i(t: Throwable?, message: String?) {
        Timber.i(t, message)
    }

    override fun i(t: Throwable?) {
        Timber.i(t)
    }

    override fun w(message: String?) {
        Timber.w(message)
    }

    override fun w(t: Throwable?, message: String?) {
        Timber.w(t, message)
    }

    override fun w(t: Throwable?) {
        Timber.w(t)
    }

    override fun e(message: String?) {
        Timber.e(message)
    }

    override fun e(t: Throwable?, message: String?) {
        Timber.e(t, message)
    }

    override fun e(t: Throwable?) {
        Timber.e(t)
    }

    override fun wtf(message: String?) {
        Timber.e(message)
    }

    override fun wtf(t: Throwable?, message: String?) {
        Timber.wtf(t, message)
    }

    override fun wtf(t: Throwable?) {
        Timber.wtf(t)
    }

    override fun log(priority: Int, message: String?) {
        Timber.log(priority, message)
    }

    override fun log(priority: Int, t: Throwable?, message: String?) {
        Timber.log(priority, t, message)
    }

    override fun log(priority: Int, t: Throwable?) {
        Timber.log(priority, t)
    }
}
