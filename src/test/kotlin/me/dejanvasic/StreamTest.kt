package me.dejanvasic;

import io.kotest.core.spec.style.DescribeSpec;
import io.kotest.matchers.shouldBe

class StreamTest : DescribeSpec({
    describe("head") {
        it("should return the head value when there is an item") {
            val stream = ConsStream({ 1 }, { EmptyStream })

            val head = stream.headOption()

            head shouldBe Some(1)
        }

        it("should return Nothing") {
            val stream = EmptyStream

            val head = stream.headOption()

            head shouldBe None
        }
    }
})
