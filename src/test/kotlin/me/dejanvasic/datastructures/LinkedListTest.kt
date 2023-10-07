package me.dejanvasic.datastructures

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class LinkedListTest : DescribeSpec({
    describe("empty") {
        it("should create a new empty list") {
            val data = LinkedList.empty<Int>()

            data shouldBe Nil
        }
    }

    describe("of") {
        it("should create a new linked list") {
            val data = LinkedList.of(1, 2, 3)

            data shouldBe Cons(1, Cons(2, Cons(3, Nil)))
        }
    }
})
