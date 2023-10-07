package me.dejanvasic.datastructures

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
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

    describe("tail") {
        it("should remove the first item and take tail which is rest of items") {
            val data = LinkedList.of(1, 2, 3)

            LinkedList.tail(data) shouldBe Cons(2, Cons(3, Nil))
        }

        it("should throw an exception on an empty list") {
            val data = LinkedList.empty<String>()

            val exception = shouldThrow<IllegalStateException> { LinkedList.tail(data) }
            exception.message shouldBe "List cannot have empty chapter_one.getTail"
        }
    }

    describe("setHead") {
        it("should set a new item at the front of the list") {
            val data = LinkedList.of(5, 3, 2)

            LinkedList.setHead(data, 4) shouldBe Cons(4, Cons(3, Cons(2, Nil)))
        }

        it("should throw an exception for an empty list") {
            val empty = LinkedList.empty<Int>()
            val exception = shouldThrow<IllegalStateException> { LinkedList.setHead(empty, 2) }

            exception.message shouldBe "Cannot replace chapter_one.getHead of a Nil list"
        }
    }

    describe("drop") {
        it("should drop the number of items from the start") {
            val data = LinkedList.of(1, 2, 3)

            LinkedList.drop(data, 1) shouldBe Cons(2, Cons(3, Nil))
        }

        it("should throw an exception when list is empty") {
            val data = LinkedList.empty<String>()

            val exception = shouldThrow<IllegalStateException> { LinkedList.drop(data, 2) }
            exception.message shouldBe "Unable to drop items from an empty list"
        }
    }
})

