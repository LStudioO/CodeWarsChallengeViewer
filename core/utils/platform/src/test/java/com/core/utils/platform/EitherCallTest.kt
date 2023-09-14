package com.core.utils.platform

import com.core.utils.functional.Either
import com.core.utils.platform.network.ApiError
import com.core.utils.platform.network.EitherCall
import com.core.utils.platform.network.NetworkError
import com.core.utils.platform.network.UnknownApiError
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import okhttp3.Request
import okio.Timeout
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertSame
import kotlin.test.assertTrue

class EitherCallTest {
    @Test
    fun `returns Either Right for a successful response`() {
        // Arrange
        val delegate: Call<Any> = mockk(relaxed = true)
        val successResponse: Response<Any> = mockk(relaxed = true)
        val responseBody: Any = mockk()
        val sut = createSut(delegate)

        every { successResponse.isSuccessful } returns true
        every { successResponse.body() } returns responseBody
        every { delegate.execute() } returns successResponse

        // Act
        val response = sut.execute()

        // Assert
        assertTrue(response.isSuccessful)
        assertEquals(responseBody, response.body()?.orNull())
    }

    @Test
    fun `returns Either Left for non-successful response`() {
        // Arrange
        val delegate: Call<Any> = mockk(relaxed = true)
        val failureResponse: Response<Any> = mockk(relaxed = true)
        val sut = createSut(delegate)

        every { failureResponse.isSuccessful } returns false
        every { failureResponse.errorBody() } returns mockk()
        every { failureResponse.errorBody()?.string() } returns "error"

        every { delegate.execute() } returns failureResponse

        // Act
        val response = sut.execute()

        // Assert
        assertTrue(response.isSuccessful)
        assertTrue(response.body()?.isLeft() ?: false)
    }

    @Test
    fun `maps IOException to NetworkError`() {
        // Arrange
        val delegate: Call<Any> = mockk(relaxed = true)
        val callback: Callback<Either<ApiError, Any>> = mockk(relaxed = true)
        val throwable: Throwable = mockk<IOException>()
        val sut = createSut(delegate)

        every { delegate.enqueue(any()) } answers {
            val capturedCallback = firstArg<Callback<Any>>()
            capturedCallback.onFailure(delegate, throwable)
        }

        // Act
        sut.enqueue(callback)

        // Assert
        val eitherResponse = slot<Response<Either<ApiError, Any>>>()
        verify { callback.onResponse(any(), capture(eitherResponse)) }
        assertTrue(eitherResponse.captured.isSuccessful)
        assertTrue(eitherResponse.captured.body()?.isLeft() ?: false)
        assertTrue(eitherResponse.captured.body()?.leftOrNull() is NetworkError)
    }

    @Test
    fun `maps non-IOException to UnknownApiError`() {
        // Arrange
        val delegate: Call<Any> = mockk(relaxed = true)
        val callback: Callback<Either<ApiError, Any>> = mockk(relaxed = true)
        val throwable: Throwable = mockk()
        val sut = createSut(delegate)

        every { delegate.enqueue(any()) } answers {
            val capturedCallback = firstArg<Callback<Any>>()
            capturedCallback.onFailure(delegate, throwable)
        }

        // Act
        sut.enqueue(callback)

        // Assert
        val eitherResponse = slot<Response<Either<ApiError, Any>>>()
        verify { callback.onResponse(any(), capture(eitherResponse)) }
        assertTrue(eitherResponse.captured.isSuccessful)
        assertTrue(eitherResponse.captured.body()?.isLeft() ?: false)
        assertTrue(eitherResponse.captured.body()?.leftOrNull() is UnknownApiError)
    }

    @Test
    fun `clones instance of EitherCall`() {
        // Arrange
        val delegate: Call<Any> = mockk(relaxed = true)
        val successType: Type = mockk()
        val sut = EitherCall(delegate, successType)

        every { delegate.clone() } returns delegate

        // Act
        val clonedCall = sut.clone() as EitherCall

        // Assert
        assertNotSame(sut, clonedCall)
        assertSame(delegate, clonedCall.delegate)
        assertSame(successType, clonedCall.successType)
    }

    @Test
    fun `delegates isExecuted`() {
        // Arrange
        val delegate: Call<Any> = mockk(relaxed = true)
        val sut = createSut(delegate)

        every { delegate.isExecuted } returns true

        // Act
        val isExecuted = sut.isExecuted

        // Assert
        assertTrue(isExecuted)
    }

    @Test
    fun `delegates cancel`() {
        // Arrange
        val delegate: Call<Any> = mockk(relaxed = true)
        val sut = createSut(delegate)

        // Act
        sut.cancel()

        // Assert
        verify { delegate.cancel() }
    }

    @Test
    fun `delegates isCanceled`() {
        // Arrange
        val delegate: Call<Any> = mockk(relaxed = true)
        val sut = createSut(delegate)

        every { delegate.isCanceled } returns true

        // Act
        val isCanceled = sut.isCanceled

        // Assert
        assertTrue(isCanceled)
    }

    @Test
    fun `delegates request`() {
        // Arrange
        val delegate: Call<Any> = mockk(relaxed = true)
        val sut = createSut(delegate)
        val request: Request = mockk()

        every { delegate.request() } returns request

        // Act
        val result = sut.request()

        // Assert
        assertSame(request, result)
    }

    @Test
    fun `delegates timeout`() {
        // Arrange
        val delegate: Call<Any> = mockk(relaxed = true)
        val sut = createSut(delegate)
        val timeout: Timeout = mockk()

        every { delegate.timeout() } returns timeout

        // Act
        val result = sut.timeout()

        // Assert
        assertSame(timeout, result)
    }

    private fun createSut(delegate: Call<Any>) =
        EitherCall(delegate, Any::class.java)
}
