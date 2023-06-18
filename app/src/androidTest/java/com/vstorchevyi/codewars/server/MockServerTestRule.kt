package com.vstorchevyi.codewars.server

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockServerTestRule(
    private val port: Int,
    private val responseDispatcher: Dispatcher,
) : TestWatcher() {
    private val server = MockWebServer().apply {
        dispatcher = responseDispatcher
    }

    override fun starting(description: Description) {
        server.start(port)
    }

    override fun finished(description: Description) {
        server.shutdown()
    }

    fun setDispatcher(dispatcher: Dispatcher) {
        server.dispatcher = dispatcher
    }
}
