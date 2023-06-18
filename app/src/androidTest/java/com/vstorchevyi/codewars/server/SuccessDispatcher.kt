package com.vstorchevyi.codewars.server

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object SuccessDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.requestUrl?.encodedPath
        return when {
            path?.contains("code-challenges/completed") == true -> {
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        FileReader.readStringFromFile(
                            "completed_challenges.json",
                        ),
                    )
            }

            else -> {
                MockResponse()
                    .setResponseCode(404)
            }
        }
    }
}
