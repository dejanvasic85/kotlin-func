package me.dejanvasic

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

    describe("dropWhile") {
        it("should keep dropping items if they match predicate") {
            val data = LinkedList.of(1, 2, 3)

            val fruitWithoutLetterN = LinkedList.dropWhile(data) { f -> f == 1 }

            fruitWithoutLetterN shouldBe Cons(2, Cons(3, Nil))
        }
    }

    describe("init") {
        it("should take the last item in the list") {
            val data = LinkedList.of(1, 2, 3)
            val lastItems = LinkedList.init(data)

            lastItems shouldBe Cons(3, Nil)
        }
    }

    describe("sum") {
        it("should sum the items specifically for Int") {
            val data = LinkedList.of(1, 2, 3)
            val sum = LinkedList.sum(data)

            sum shouldBe 6
        }
    }

    describe("product") {
        it("should return the product of all items") {
            val data = LinkedList.of<Double>(
                2.toDouble(), 2.toDouble(), 2.toDouble()
            )

            val product = LinkedList.product(data)

            product shouldBe 8
        }
    }

    describe("length") {
        it("should return the number of items in the list") {
            val data = LinkedList.of(1, 2, 3)

            LinkedList.length(data) shouldBe 3
        }
    }

    describe("reverse") {
        it("should reverse the items from right to left") {
            val data = LinkedList.of(1, 2, 3)
            val reversed = LinkedList.reverse(data)

            reversed shouldBe Cons(3, Cons(2, Cons(1, Nil)))
        }
    }

    describe("append") {
        it("should add an item to the end of the list") {
            val data = LinkedList.of(1, 2, 3)
            val appended = LinkedList.append(data, Cons(4, Cons(5, Nil)))

            appended shouldBe Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
        }
    }

    describe("concat") {
        it("should concatenate two lists together") {
            val first = LinkedList.of(1, 2)
            val second = LinkedList.of(3, 4)
            val both = LinkedList.of(first, second)

            val concatenated = LinkedList.concat(both)

            concatenated shouldBe Cons(1, Cons(2, Cons(3, Cons(4, Nil))))
        }
    }

    describe("increment") {
        it("should increase each item in the list by one") {
            val data = LinkedList.of(1, 2)
            val result = LinkedList.increment(data)

            result shouldBe Cons(2, Cons(3, Nil))
        }
    }

    describe("doubleToString") {
        it("should convert each item from double to string") {
            val data = LinkedList.of(1.toDouble(), 2.toDouble())
            val result = LinkedList.doubleToString(data)

            result shouldBe Cons("1.0", Cons("2.0", Nil))
        }
    }

    describe("map") {
        it("should apply the provided function on each item") {
            val data = LinkedList.of(1, 2, 3)
            val mapped = LinkedList.map(data) { f -> f.toString() }

            mapped shouldBe Cons("1", Cons("2", Cons("3", Nil)))
        }
    }
})

