package com.core.utils.platform.network

sealed class ApiError : Exception()

data class HttpError(val code: Int, val body: String) : ApiError()

data class NetworkError(val throwable: Throwable) : ApiError()

data class UnknownApiError(val throwable: Throwable) : ApiError()
