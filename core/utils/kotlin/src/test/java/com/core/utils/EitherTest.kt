package com.core.utils

import com.core.utils.functional.Either
import com.core.utils.functional.filterOrElse
import com.core.utils.functional.flatMap
import com.core.utils.functional.getOrElse
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EitherTest {

    @Test
    fun `isLeft returns true when Either is Left`() {
        // Arrange
        val either: Either<String, Int> = Either.Left("Error")

        // Act
        val result = either.isLeft()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `isRight returns true when Either is Right`() {
        // Arrange
        val either: Either<String, Int> = Either.Right(42)

        // Act
        val result = either.isRight()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `fold applies ifLeft function for Left and returns the result`() {
        // Arrange
        val either: Either<String, Int> = Either.Left("test")

        // Act
        val result = either.fold(
            ifLeft = { error -> "Error: $error" },
            ifRight = { value -> "Value: $value" },
        )

        // Assert
        assertEquals("Error: test", result)
    }

    @Test
    fun `fold applies ifRight function for Right and returns the result`() {
        // Arrange
        val either: Either<String, Int> = Either.Right(42)

        // Act
        val result = either.fold(
            ifLeft = { error -> "Error: $error" },
            ifRight = { value -> "Value: $value" },
        )

        // Assert
        assertEquals("Value: 42", result)
    }

    @Test
    fun `swap returns a new Either with Left and Right sides swapped`() {
        // Arrange
        val either: Either<String, Int> = Either.Left("Error")

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
    fun `map applies the function to the value of Right and returns a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.Right(42)

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
    fun `mapLeft applies the function to the value of Left and return a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.Left("Error")

        // Act
        val result = either.mapLeft { error -> error.length }

        // Assert
        assertTrue(result.isLeft())
        assertEquals(
            5,
            result.leftOrNull(),
        )
    }

    @Test
    fun `bimap applies leftOperation if Left and returns a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.Left("Error")

        // Act
        val result = either.bimap(
            leftOperation = { error -> error.length },
            rightOperation = { value -> "Value: $value" },
        )

        // Assert
        assertTrue(result.isLeft())
        assertEquals(
            5,
            result.leftOrNull(),
        )
    }

    @Test
    fun `bimap applies rightOperation if Right and return a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.Right(42)

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
    fun `orNull returns the value of Right if exists`() {
        // Arrange
        val either: Either<String, Int> = Either.Right(42)

        // Act
        val value = either.orNull()

        // Assert
        assertEquals(42, value)
    }

    @Test
    fun `orNull returns null if it's not Right`() {
        // Arrange
        val either: Either<String, Int> = Either.Left("Error")

        // Act
        val result = either.orNull()

        // Assert
        assertNull(result)
    }

    @Test
    fun `leftOrNull returns the value of Left if exists`() {
        // Arrange
        val either: Either<Int, String> = Either.Left(42)

        // Act
        val value = either.leftOrNull()

        // Assert
        assertEquals(42, value)
    }

    @Test
    fun `leftOrNull returns null if it's not Left`() {
        // Arrange
        val either: Either<Int, String> = Either.Right("Success")

        // Act
        val result = either.leftOrNull()

        // Assert
        assertNull(result)
    }

    @Test
    fun `getOrElse returns Right value`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.getOrElse { 5 }

        // Assert
        assertEquals(10, result)
    }

    @Test
    fun `getOrElse returns default value when is Left`() {
        // Arrange
        val left: Either<String, Int> = Either.Left("Error")

        // Act
        val result = left.getOrElse { 5 }

        // Assert
        assertEquals(5, result)
    }

    @Test
    fun `ifLeft applies the function if Left and returns a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.Left("Error")
        val sb = StringBuilder()

        // Act
        val result = either.ifLeft { error -> sb.append(error) }

        // Assert
        assertTrue(result.isLeft())
        assertEquals("Error", sb.toString())
    }

    @Test
    fun `ifRight applies the function if Right and return a new Either`() {
        // Arrange
        val either: Either<String, Int> = Either.Right(42)
        val sb = StringBuilder()

        // Act
        val result = either.ifRight { value -> sb.append(value) }

        // Assert
        assertTrue(result.isRight())
        assertEquals("42", sb.toString())
    }

    @Test
    fun `flatMap produces a new Right Either value`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.flatMap { Either.Right("Success") }

        // Assert
        assertTrue(result is Either.Right)
        assertEquals("Success", result.value)
    }

    @Test
    fun `flatMap produces a new Left Either value`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.flatMap { Either.Left("Error") }

        // Assert
        assertTrue(result is Either.Left)
        assertEquals("Error", result.value)
    }

    @Test
    fun `flatMap does nothing if is Left`() {
        // Arrange
        val left: Either<String, Int> = Either.Left("Error")

        // Act
        val result = left.flatMap { Either.Right(5) }

        // Assert
        assertTrue(result is Either.Left)
    }

    @Test
    fun `filterOrElse with valid predicate`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.filterOrElse({ it > 5 }, { "Less than 5" })

        // Assert
        assertTrue(result is Either.Right)
    }

    @Test
    fun `filterOrElse with invalid predicate`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.filterOrElse({ it > 15 }, { "Less than 15" })

        // Assert
        assertTrue(result is Either.Left)
        assertEquals("Less than 15", result.value)
    }

    @Test
    fun `filterOrElse with Left value`() {
        // Arrange
        val left: Either<String, Int> = Either.Left("Error")

        // Act
        val result = left.filterOrElse({ it > 5 }, { "Less than 5" })

        // Assert
        assertTrue(result is Either.Left)
    }

    @Test
    fun `foldLeft applies rightOperation when is Right`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.foldLeft("Start") { acc, value -> acc + value }

        // Assert
        assertEquals("Start10", result)
    }

    @Test
    fun `foldLeft returns initial when is Left`() {
        // Arrange
        val left: Either<String, Int> = Either.Left("Error")

        // Act
        val result = left.foldLeft("Start") { acc, value -> acc + value.toString() }

        // Assert
        assertEquals("Start", result)
    }

    @Test
    fun `foldRight applies rightOperation when is Right`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.foldRight("End") { value, acc -> value.toString() + acc }

        // Assert
        assertEquals("10End", result)
    }

    @Test
    fun `foldRight returns initial when is Left`() {
        // Arrange
        val left: Either<String, Int> = Either.Left("Error")

        // Act
        val result = left.foldRight("End") { _, acc -> acc }

        // Assert
        assertEquals("End", result)
    }

    @Test
    fun `bifoldLeft with Right value`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.bifoldLeft(
            c = "Start",
            f = { acc, leftValue -> acc + leftValue },
            g = { acc, rightValue -> acc + rightValue },
        )

        // Assert
        assertEquals("Start10", result)
    }

    @Test
    fun `bifoldLeft with Left value`() {
        // Arrange
        val left: Either<String, Int> = Either.Left("Error")

        // Act
        val result = left.bifoldLeft(
            c = "Start",
            f = { acc, leftValue -> acc + leftValue },
            g = { acc, _ -> acc },
        )

        // Assert
        assertEquals("StartError", result)
    }

    @Test
    fun `bifoldRight with Right value`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.bifoldRight(
            c = "End",
            f = { leftValue, acc -> leftValue + acc },
            g = { rightValue, acc -> rightValue.toString() + acc },
        )

        // Assert
        assertEquals("10End", result)
    }

    @Test
    fun `bifoldRight with Left value`() {
        // Arrange
        val left: Either<String, Int> = Either.Left("Error")

        // Act
        val result = left.bifoldRight(
            c = "End",
            f = { leftValue, acc -> leftValue + acc },
            g = { _, acc -> acc },
        )

        // Assert
        assertEquals("ErrorEnd", result)
    }

    @Test
    fun `exists valid predicate`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.exists { it > 5 }

        // Assert
        assertTrue(result)
    }

    @Test
    fun `exists invalid predicate`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.exists { it < 5 }

        // Assert
        assertFalse(result)
    }

    @Test
    fun `exists with Left`() {
        // Arrange
        val left: Either<String, Int> = Either.Left("Error")

        // Act
        val result = left.exists { true }

        // Assert
        assertFalse(result)
    }

    @Test
    fun fproduct() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.fproduct { it * 2 }

        // Assert
        assertEquals(Either.Right(10 to 20), result)
    }

    @Test
    fun tupleRight() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.tupleRight(20)

        // Assert
        assertEquals(Either.Right(10 to 20), result)
    }

    @Test
    fun tupleLeft() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.tupleLeft(20)

        // Assert
        assertEquals(Either.Right(20 to 10), result)
    }

    @Test
    fun replicate() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.replicate(3)

        // Assert
        assertEquals(Either.Right(listOf(10, 10, 10)), result)
    }

    @Test
    fun traverse() {
        // Arrange
        val right = Either.Right(1)

        // Act
        val result = right.traverse { a -> listOf(a, a * 2) }

        // Assert
        assertEquals(listOf(Either.Right(1), Either.Right(2)), result)
    }

    @Test
    fun `findOrNull success`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.findOrNull { it < 15 }

        // Assert
        assertEquals(10, result)
    }

    @Test
    fun `findOrNull not found`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.findOrNull { it > 15 }

        // Assert
        assertNull(result)
    }

    @Test
    fun `findOrNull with Left`() {
        // Arrange
        val left: Either<Int, Int> = Either.Left(10)

        // Act
        val result = left.findOrNull { it > 5 }

        // Assert
        assertNull(result)
    }

    @Test
    fun `isEmpty with Right`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.isEmpty()

        // Assert
        assertFalse(result)
    }

    @Test
    fun `isEmpty with Left`() {
        // Arrange
        val left: Either<Int, String> = Either.Left(10)

        // Act
        val result = left.isEmpty()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `isNotEmpty with Right`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = right.isNotEmpty()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `isNotEmpty with Left`() {
        // Arrange
        val left: Either<Int, String> = Either.Left(10)

        // Act
        val result = left.isNotEmpty()

        // Assert
        assertFalse(result)
    }

    @Test
    fun `conditionally with valid condition`() {
        // Arrange
        val condition = true
        val successValue = 10

        // Act
        val result = Either.conditionally(condition, { "Error" }, { successValue })

        // Assert
        assertTrue(result is Either.Right)
        assertEquals(successValue, result.value)
    }

    @Test
    fun `conditionally with invalid condition`() {
        // Arrange
        val condition = false

        // Act
        val result = Either.conditionally(condition, { "Error" }, { 10 })

        // Assert
        assertTrue(result is Either.Left)
        assertEquals("Error", result.value)
    }

    @Test
    fun `catch with no exception`() {
        // Act
        val result = Either.catch { 10 }

        // Assert
        assertTrue(result is Either.Right)
        assertEquals(10, result.value)
    }

    @Test
    fun `catch with non fatal exception`() {
        // Arrange & Act
        val result = Either.catch { throw Exception("Error") }

        // Assert
        assertTrue(result is Either.Left)
        assertTrue(result.value is Exception)
        assertEquals("Error", result.value.message)
    }

    @Test(expected = CancellationException::class)
    fun `catch doesn't swallow CancellationException`() {
        // Act
        Either.catch {
            throw CancellationException()
        }
    }

    @Test(expected = OutOfMemoryError::class)
    fun `catch doesn't swallow fatal exception`() {
        // Act
        Either.catch { throw OutOfMemoryError("Error") }
    }

    @Test
    fun `catchAndFlatten with no exception`() {
        // Arrange & Act
        val result = Either.catchAndFlatten { Either.Right(10) }

        // Assert
        assertTrue(result is Either.Right)
        assertEquals(10, result.value)
    }

    @Test
    fun `catchAndFlatten with exception`() {
        // Act
        val result = Either.catchAndFlatten<Int> { throw Exception("Error") }

        // Assert
        assertTrue(result is Either.Left)
        assertTrue(result.value is Exception)
        assertEquals("Error", result.value.message)
    }

    @Test(expected = CancellationException::class)
    fun `catchAndFlatten doesn't swallow CancellationException`() {
        // Act
        Either.catchAndFlatten<Int> {
            throw CancellationException()
        }
    }

    @Test(expected = OutOfMemoryError::class)
    fun `catchAndFlatten doesn't swallow fatal exception`() {
        // Act
        Either.catchAndFlatten<Int> {
            throw OutOfMemoryError("Error")
        }
    }

    @Test
    fun `test lift with transformation`() {
        // Arrange
        val function = Either.lift<String, Int, Int> { it * 2 }
        val right: Either<String, Int> = Either.Right(10)

        // Act
        val result = function(right)

        // Assert
        assertTrue(result is Either.Right)
        assertEquals(20, result.value)
    }

    @Test
    fun `test mapN with two Right values`() {
        // Arrange
        val right1: Either<String, Int> = Either.Right(10)
        val right2: Either<String, Int> = Either.Right(20)

        // Act
        val result = Either.mapN(right1, right2) { a, b -> a + b }

        // Assert
        assertTrue(result is Either.Right)
        assertEquals(30, result.value)
    }

    @Test
    fun `test mapN with one Left value`() {
        // Arrange
        val right: Either<String, Int> = Either.Right(10)
        val left: Either<String, Int> = Either.Left("Error")

        // Act
        val result = Either.mapN(right, left) { a, b -> a + b }

        // Assert
        assertTrue(result is Either.Left)
        assertEquals("Error", result.value)
    }
}
