package me.dejanvasic;

import io.kotest.core.spec.style.DescribeSpec;
import io.kotest.matchers.shouldBe

class StreamTest : DescribeSpec({
    describe("headOption") {
        it("should return the head value when there is an item") {
            val stream = StreamCons({ 1 }, { StreamEmpty })

            val head = stream.headOption()

            head shouldBe Some(1)
        }

        it("should return Nothing") {
            val stream = StreamEmpty

            val head = stream.headOption()

            head shouldBe None
        }
    }

    describe("length") {
        it("should return the number of items in the stream") {
            val items = Stream.of(1, 1, 1)

            items.length() shouldBe 3
        }
    }

    describe("of") {
        it("should create a stream of numbers") {
            val stream = Stream.of(1, 2)

            val result = stream.headOption()

            result shouldBe Some(1)
        }
    }

    describe("toList") {
        it("should return a list from a stream") {
            val stream = Stream.of("one", "two", "three")

            val result = stream.toList()

            result shouldBe Cons("one", Cons("two", Cons("three", Nil)))
        }
    }

    describe("take") {
        it("should return the first item") {
            val stream = Stream.of(1, 2)

            val first = stream.take(1)

            first.headOption() shouldBe Some(1)
        }
    }

    describe("drop") {
        it("should return the last element by skipping the first two") {
            val stream = Stream.of(
                1, 2, 3
            )

            val dropped = stream.drop(2)

            dropped.headOption() shouldBe Some(3)
        }
    }

    describe("takeWhile") {
        it("should return the values that meet the predicate criteria") {
            val stream = Stream.of(1, 2, 3, 4, 5).takeWhile { a -> a % 2 == 0 }

            stream.length() shouldBe 2
            stream.headOption() shouldBe Some(2)
        }
    }

    describe("foldRight") {
        it("should reduce the stream by adding all the values together") {
            val stream = Stream.of(1, 2, 3)

            val sum = stream.foldRight({ 0 }) { a, b -> a + b() }

            sum shouldBe 6
        }
    }

    describe("forAll") {
        it("should return true when all items meet the criteria") {
            val stream = Stream.of(1, 2, 3)
            val areAllGreaterThanZero = stream.forAll { a -> a > 0 }

            areAllGreaterThanZero shouldBe true
        }

        it("should return false when one items is false") {
            val stream = Stream.of(1, 2, 3)
            val areAllOne = stream.forAll { a -> a == 1 }

            areAllOne shouldBe false
        }
    }

    describe("map") {
        it("should map all values from Int to String") {
            val stream = Stream.of(1, 2, 3)

            val mapped = stream.map { a -> a.toString() }

            mapped.length() shouldBe 3
            mapped.headOption() shouldBe Some("1")
        }
    }

    describe("append") {
        it("should add another stream to the end") {
            val stream = Stream.of(1).append(Stream.of(2))

            stream.length() shouldBe 2
        }
    }

    describe("flatMap") {
        it("should flat map all Stream values") {
            val stream = Stream.of(1, 2, 3)

            val flat = stream.flatMap { a -> Stream.of(a.toString()) }

            flat.headOption() shouldBe Some("1")
            flat.length() shouldBe 3
        }
    }

    describe("constantStream") {
        it("should create an infinite stream of constant values") {
            val stream = Stream.constant("a")

            val threeItems = stream.take(3)

            threeItems.length() shouldBe 3
            threeItems.headOption() shouldBe Some("a")
        }
    }

    describe("from") {
        it("should generate an infinite incremental numbers") {
            val result = Stream.from(5).take(3).takeWhile { a -> a > 6 }

            result.headOption() shouldBe Some(7)
        }
    }

    describe("fibs") {
        it("should generate an infinite stream of fibonnaci numbers in sequence") {
            val result = Stream.fibs().drop(3).take(4).map { a -> a.toString() }

            result.length() shouldBe 4
            result.headOption() shouldBe Some("2")
        }
    }
})
