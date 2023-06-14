/* Copyright (C) 2017 The Arrow Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.core.utils.functional

import com.core.utils.functional.Either.Left
import com.core.utils.functional.Either.Right

sealed class Either<out A, out B> : EitherOf<A, B> {

    /**
     * Returns `true` if this is a [Right], `false` otherwise.
     * Used only for performance instead of fold.
     */
    internal abstract val isRight: Boolean

    /**
     * Returns `true` if this is a [Left], `false` otherwise.
     * Used only for performance instead of fold.
     */
    internal abstract val isLeft: Boolean

    fun isLeft(): Boolean = isLeft

    fun isRight(): Boolean = isRight

    /**
     * Applies `ifLeft` if this is a [Left] or `ifRight` if this is a [Right].
     *
     * Example:
     * ```
     * val result: Either<Exception, Value> = possiblyFailingOperation()
     * result.fold(
     *      { log("operation failed with $it") },
     *      { log("operation succeeded with $it") }
     * )
     * ```
     *
     * @param ifLeft the function to apply if this is a [Left]
     * @param ifRight the function to apply if this is a [Right]
     * @return the results of applying the function
     */
    inline fun <C> fold(ifLeft: (A) -> C, ifRight: (B) -> C): C = when (this) {
        is Right -> ifRight(b)
        is Left -> ifLeft(a)
    }

    /**
     * If this is a `Left`, then return the left value in `Right` or vice versa.
     *
     * Example:
     * ```
     * Left("left").swap()   // Result: Right("left")
     * Right("right").swap() // Result: Left("right")
     * ```
     */
    fun swap(): Either<B, A> =
        fold({ Right(it) }, { Left(it) })

    /**
     * The given function is applied if this is a `Right`.
     *
     * Example:
     * ```
     * Right(12).map { "flower" } // Result: Right("flower")
     * Left(12).map { "flower" }  // Result: Left(12)
     * ```
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <C> map(f: (B) -> C): Either<A, C> =
        flatMap { Right(f(it)) }

    /**
     * The given function is applied if this is a `Left`.
     *
     * Example:
     * ```
     * Right(12).mapLeft { "flower" } // Result: Right(12)
     * Left(12).mapLeft { "flower" }  // Result: Left("flower)
     * ```
     */
    inline fun <C> mapLeft(f: (A) -> C): Either<C, B> =
        fold({ Left(f(it)) }, { Right(it) })

    /**
     * Map over Left and Right of this Either
     */
    inline fun <C, D> bimap(leftOperation: (A) -> C, rightOperation: (B) -> D): Either<C, D> =
        fold({ Left(leftOperation(it)) }, { Right(rightOperation(it)) })

    /**
     * Returns the right value if it exists, otherwise null
     *
     * Example:
     * ```kotlin:ank:playground
     * import com.core.utils.functional.Right
     * import com.core.utils.functional.Left
     *
     * //sampleStart
     * val right = Right(12).orNull() // Result: 12
     * val left = Left(12).orNull()   // Result: null
     * //sampleEnd
     * fun main() {
     *   println("right = $right")
     *   println("left = $left")
     * }
     * ```
     */
    fun orNull(): B? = fold({ null }, { it })

    inline fun ifLeft(f: (A) -> Unit): Either<A, B> =
        fold({ f(it); Left(it) }, { Right(it) })

    inline fun ifRight(f: (B) -> Unit): Either<A, B> =
        fold({ Left(it) }, { f(it); Right(it) })

    /**
     * The left side of the disjoint union, as opposed to the [Right] side.
     */
    @Suppress("DataClassPrivateConstructor")
    data class Left<out A> @PublishedApi internal constructor(val a: A) : Either<A, Nothing>() {
        override val isLeft
            get() = true
        override val isRight
            get() = false

        companion object {
            operator fun <A> invoke(a: A): Either<A, Nothing> = Left(a)
        }
    }

    /**
     * The right side of the disjoint union, as opposed to the [Left] side.
     */
    @Suppress("DataClassPrivateConstructor")
    data class Right<out B> @PublishedApi internal constructor(val b: B) : Either<Nothing, B>() {
        override val isLeft
            get() = false
        override val isRight
            get() = true

        companion object {
            operator fun <B> invoke(b: B): Either<Nothing, B> = Right(b)
        }
    }

    companion object {
        fun <L> left(left: L): Either<L, Nothing> = Left(left)

        fun <R> right(right: R): Either<Nothing, R> = Right(right)
    }
}

@SuppressWarnings("all")
fun <L> Left(left: L): Either<L, Nothing> = Left(left)

@SuppressWarnings("all")
fun <R> Right(right: R): Either<Nothing, R> = Right(right)

/**
 * Binds the given function across [Either.Right].
 *
 * @param f The function to bind across [Either.Right].
 */
inline fun <A, B, C> EitherOf<A, B>.flatMap(f: (B) -> Either<A, C>): Either<A, C> =
    fix().let {
        when (it) {
            is Right -> f(it.b)
            is Left -> it
        }
    }

fun <A, B> Either<A, B>.leftValue(): A? {
    return when (this) {
        is Left -> a
        else -> null
    }
}

class ForEither private constructor() {
    companion object
}
typealias EitherOf<A, B> = Kind2<ForEither, A, B>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <A, B> EitherOf<A, B>.fix(): Either<A, B> =
    this as Either<A, B>

/**
 * `Kind<F, A>` represents a generic `F<A>` in a way that's allowed by Kotlin.
 * To revert it back to its original form use the extension function `fix()`.
 */
interface Kind<out F, out A>
typealias Kind2<F, A, B> = Kind<Kind<F, A>, B>
