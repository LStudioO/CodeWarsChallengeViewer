package com.core.utils.platform

import com.core.utils.platform.network.EitherCall
import com.core.utils.platform.network.EitherCallAdapter
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertSame
import org.junit.Test
import retrofit2.Call
import java.lang.reflect.Type

class EitherCallAdapterTest {

    @Test
    fun `returns EitherCall instance with delegate and responseType`() {
        // Arrange
        val responseType: Type = mockk()
        val call: Call<Any> = mockk()
        val adapter = EitherCallAdapter<Any>(responseType)

        // Act
        val adaptedCall = adapter.adapt(call)

        // Assert
        assertEquals(EitherCall::class.java, adaptedCall.javaClass)
        assertSame(call, (adaptedCall as EitherCall<Any>).delegate)
        assertEquals(responseType, adaptedCall.successType)
    }

    @Test
    fun `doesn't modify responseType`() {
        // Arrange
        val responseType: Type = mockk()
        val adapter = EitherCallAdapter<Any>(responseType)

        // Act
        val result = adapter.responseType()

        // Assert
        assertEquals(responseType, result)
    }
}
