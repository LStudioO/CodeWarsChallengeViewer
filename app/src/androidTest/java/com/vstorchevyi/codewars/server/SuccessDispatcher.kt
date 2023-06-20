package com.vstorchevyi.codewars.server

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

data class SuccessDispatcher(
    private val userId: String,
) : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.requestUrl?.encodedPath
        return when {
            path?.contains("users/$userId/code-challenges/completed") == true -> {
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        FileReader.readStringFromFile(
                            "completed_challenges.json",
                        ),
                    )
            }

            path?.contains("code-challenges/") == true -> {
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        FileReader.readStringFromFile(
                            "challenge.json",
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
