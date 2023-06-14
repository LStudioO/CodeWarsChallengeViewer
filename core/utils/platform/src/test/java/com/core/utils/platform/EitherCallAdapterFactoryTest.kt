package com.core.utils.platform

import com.core.utils.functional.Either
import com.core.utils.platform.network.ApiError
import com.core.utils.platform.network.EitherCallAdapter
import com.core.utils.platform.network.EitherCallAdapterFactory
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import retrofit2.Call
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class EitherCallAdapterFactoryTest {

    data class Dto(
        val id: Int,
    )

    @Test
    fun `returns EitherCallAdapter for Call of Either(ApiError, Dto)`() {
        // Arrange
        val sut = createSut()
        val returnType = createParameterizedType(
            Call::class.java,
            createParameterizedType(Either::class.java, ApiError::class.java, Dto::class.java),
        )
        val annotations = emptyArray<Annotation>()
        val retrofit = mockk<Retrofit>()

        // Act
        val adapter = sut.get(returnType, annotations, retrofit)

        // Assert
        assertEquals(EitherCallAdapter::class.java, adapter?.javaClass)
        assertEquals(Dto::class.java, (adapter as EitherCallAdapter<*>).responseType)
    }

    @Test
    fun `returns null for non-Call return type`() {
        // Arrange
        val sut = createSut()
        val returnType = String::class.java
        val annotations = emptyArray<Annotation>()
        val retrofit = mockk<Retrofit>()

        // Act
        val adapter = sut.get(returnType, annotations, retrofit)

        // Assert
        assertNull(adapter)
    }

    @Test(expected = IllegalStateException::class)
    fun `throws IllegalStateException for non-parameterized Call return type`() {
        // Arrange
        val sut = createSut()
        val returnType = Call::class.java
        val annotations = emptyArray<Annotation>()
        val retrofit = mockk<Retrofit>()

        // Act
        sut.get(returnType, annotations, retrofit)
    }

    @Test(expected = IllegalStateException::class)
    fun `throws IllegalStateException for non-parameterized Either response type`() {
        // Arrange
        val sut = createSut()
        val returnType = createParameterizedType(
            Call::class.java,
            Either::class.java,
        )
        val annotations = emptyArray<Annotation>()
        val retrofit = mockk<Retrofit>()

        // Act
        sut.get(returnType, annotations, retrofit)
    }

    @Test
    fun `returns null for non-ApiError left type`() {
        // Arrange
        val sut = createSut()
        val returnType = createParameterizedType(
            Call::class.java,
            createParameterizedType(Either::class.java, String::class.java, Dto::class.java),
        )
        val annotations = emptyArray<Annotation>()
        val retrofit = mockk<Retrofit>()

        // Act
        val adapter = sut.get(returnType, annotations, retrofit)

        // Assert
        assertNull(adapter)
    }

    private fun createParameterizedType(
        rawType: Class<*>,
        vararg typeArguments: Type,
    ): ParameterizedType {
        return object : ParameterizedType {
            override fun getRawType(): Type = rawType
            override fun getOwnerType(): Type? = null
            override fun getActualTypeArguments(): Array<out Type> = typeArguments
        }
    }

    private fun createSut() = EitherCallAdapterFactory()
}
