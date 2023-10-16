package me.dejanvasic

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

class EitherTest : DescribeSpec({
    describe("mean") {
        it("should return a Left value when list is empty") {
            val emptyList = Nil
            val result = Either.mean(emptyList)

            result shouldBe Left("List cannot be empty to produce a mean value")
        }

        it("should return a Right value with the mean when doubles are present") {
            val data = LinkedList.of(1.5, 3.5, 4.0)
            val result = Either.mean(data)

            result shouldBe Right(3.0)
        }
    }

    describe("catches") {
        it("should return a Left with an exception including stack trace") {
            val result = Either.catches { "hello".toInt() }

            result.shouldBeTypeOf<Left<String>>()
        }
    }

    describe("map") {
        it("should call the map function") {
            val result = Right(1).map { x -> x * 2 }

            result shouldBe Right(2)
        }

        it("should return left") {
            val result = Left("hello").map { v: String -> v.length }

            result shouldBe Left("hello")
        }
    }

    describe("orElse") {
        it("should return Right when Left is supplied") {
            val result = Left("hello").orElse { Right("world") }

            result shouldBe Right("world")
        }

        it("should return Left when Right is supplied") {
            val result = Right("hello").orElse { Left("world") }

            result shouldBe Right("hello")
        }
    }

    describe("flatMap") {
        it("should return Right mapped function") {
            val result = Right("hello").flatMap { a -> Right("$a world") }

            result shouldBe Right("hello world")
        }

        it("should return Left") {
            val result = Left("hello").flatMap { a -> Right("$a world") }

            result shouldBe Left("hello")
        }
    }

    describe("map2") {
        it("should return Right when both are Right and function evaluated") {
            val result = Either.map2(Right("hello"), Right("world")) { a, b -> "$a $b" }

            result shouldBe Right("hello world")
        }

        it("should return Left when first one is Left") {
            val result = Either.map2(Left("hello"), Right("world")) { a, b -> "$a $b" }

            result shouldBe Left("hello")
        }

        it("should return Left when second one is Left") {
            val result = Either.map2(Right("hello"), Left(1)) { a, b -> "$a $b" }

            result shouldBe Left(1)
        }
    }

    describe("traverse") {
        it("should return Right with a mapped list") {
            val result = Either.traverse(LinkedList.of(1, 2, 3)) { a -> Right(a * 2) }

            result shouldBe Right(Cons(2, Cons(4, Cons(6, Nil))))
        }

        it("should return Left when one of the items is left") {
            val result = Either.traverse(LinkedList.of(1, 2, 3)) { a ->
                if (a == 2) Left("Value cannot be 2") else Right(a * 2)
            }

            result shouldBe Left("Value cannot be 2")
        }
    }

    describe("sequence") {
        it("should return Right when all items are Right") {
            val result = Either.sequence(LinkedList.of(Right("1"), Right("2")))

            result shouldBe Right(LinkedList.of("1", "2"))
        }
    }
})
