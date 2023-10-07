package me.dejanvasic

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UtilTest : DescribeSpec({
    describe("curry") {
        it("should return two functions") {
            val func = curry<String, String, Int> { a, b -> a.length + b.length }

            func("hello")("world") shouldBe 10
        }
    }

    describe("uncurry") {
        it("should return a function that can be invoked with two arguments") {
            val func = uncurry<String, String, Int> { a -> { b -> a.length + b.length } }

            func("hello", "world") shouldBe 10
        }
    }

    describe("compose") {
        it("should allow two functions to be composed") {
            val func = compose<Int, Int, Int>({ a -> a + 1 }, { b -> b * 2 })

            func(10) shouldBe 21
        }
    }
})
