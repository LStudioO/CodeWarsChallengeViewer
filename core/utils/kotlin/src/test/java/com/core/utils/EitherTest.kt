package com.core.utils

import com.core.utils.functional.Either
import com.core.utils.functional.leftValue
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EitherTest {

    @Test
    fun `isLeft should return true when Either is Left`() {
        // Arrange
        val either: Either<String, Int> = Either.left("Error")

        // Act
        val result = either.isLeft()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `isRight should return true when Either is Right`() {
        // Arrange
        val either: Either<String, Int> = Either.right(42)

        // Act
        val result = either.isRight()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `fold should apply ifLeft function for Left and return the result`() {
        // Arrange
        val either: Either<String, Int> = Either.left("test")

        // Act
        val result = either.fold(
            ifLeft = { error -> "Error: $error" },
            ifRight = { value -> "Value: $value" },
        )

        // Assert
        assertEquals("Error: test", result)
    }

    @Test
    fun `fold should apply ifRight function for Right and return the result`() {
        // Arrange
        val either: Either<String, Int> = Either.right(42)

        // Act
        val result = either.fold(
            ifLeft = { error -> "Error: $error" },
            ifRight = { value -> "Value: $value" },
        )

        // Assert
        assertEquals("Value: 42", result)
    }

    @Test
    fun `swap should return a new Either with Left and Right sides swapped`() {
        // Arrange
        val either: Either<String, Int> = Either.left("Error")

        // Act
        val result = either.swap()

        // Assert
        assertTrue(result.isRight())
        assertEquals(
            "Error",
            result.orNull(),
        )
    }

    @Test
    fun `map should apply the function to the value of Right and return a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.right(42)

        // Act
        val result = either.map { value -> "Value: $value" }

        // Assert
        assertTrue(result.isRight())
        assertEquals(
            "Value: 42",
            result.orNull(),
        )
    }

    @Test
    fun `mapLeft should apply the function to the value of Left and return a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.left("Error")

        // Act
        val result = either.mapLeft { error -> error.length }

        // Assert
        assertTrue(result.isLeft())
        assertEquals(
            5,
            result.leftValue(),
        )
    }

    @Test
    fun `bimap should apply leftOperation if Left and return a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.left("Error")

        // Act
        val result = either.bimap(
            leftOperation = { error -> error.length },
            rightOperation = { value -> "Value: $value" },
        )

        // Assert
        assertTrue(result.isLeft())
        assertEquals(
            5,
            result.leftValue(),
        )
    }

    @Test
    fun `bimap should apply rightOperation if Right and return a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.right(42)

        // Act
        val result = either.bimap(
            leftOperation = { error -> error.length },
            rightOperation = { value -> "Value: $value" },
        )

        // Assert
        assertTrue(result.isRight())
        assertEquals(
            "Value: 42",
            result.orNull(),
        )
    }

    @Test
    fun `orNull should return the value of Right if exists, otherwise null`() {
        // Arrange
        val either: Either<String, Int> = Either.right(42)

        // Act
        val value = either.orNull()

        // Assert
        assertEquals(42, value)
    }

    @Test
    fun `orNull should return null if it's not Right`() {
        // Arrange
        val either: Either<String, Int> = Either.left("Error")

        // Act
        val result = either.orNull()

        // Assert
        assertNull(result)
    }

    @Test
    fun `ifLeft should apply the function if Left and return a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.left("Error")
        val sb = StringBuilder()

        // Act
        val result = either.ifLeft { error -> sb.append(error) }

        // Assert
        assertTrue(result.isLeft())
        assertEquals("Error", sb.toString())
    }

    @Test
    fun `ifRight should apply the function if Right and return a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.right(42)
        val sb = StringBuilder()

        // Act
        val result = either.ifRight { value -> sb.append(value) }

        // Assert
        assertTrue(result.isRight())
        assertEquals("42", sb.toString())
    }
}
