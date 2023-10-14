package me.dejanvasic

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class OptionTest : DescribeSpec({
    describe("map") {
        it("should to a different value when it has a value") {
            val data = Some("hello")
            val mapped = data.map { f -> f.length }

            mapped shouldBe Some(5)
        }

        it("should return nil when has no value") {
            val data = None
            val mapped = data.map { f -> f }

            mapped shouldBe None
        }
    }

    describe("getOrElse") {
        it("should return value otherwise default") {
            val data = Some("foo")
            val value = data.getOrElse { "bar" }

            value shouldBe "foo"
        }
    }

    describe("orElse") {
        it("should return a value") {
            val data = Some("foo")
            val value = data.orElse { Some("what") }

            value shouldBe Some("foo")
        }

        it("should return another option") {
            val data = None
            val value = data.orElse { Some("hello") }

            value shouldBe Some("hello")
        }
    }

    describe("filter") {
        it("should return Option when a predicate is true") {
            val data = Some("hello")
            val value = data.filter { f -> f.length == 5 }

            value shouldBe Some("hello")
        }

        it("should return None when a predicate is false") {
            val data = Some("world")
            val value = data.filter { f -> f.length == 10 }

            value shouldBe None
        }
    }

    describe("flatMap") {
        it("should return mapped object to another Option") {
            val data = Some("hello")
            val flatMapped = data.flatMap { d -> Some("$d mapped") }

            flatMapped shouldBe Some("hello mapped")
        }
    }

    describe("map2") {
        it("should lift both parameters to Options and return an option for a given function") {
            val data = Option.map2(Some("hello"), Some("world")) { a, b -> "$a $b" }

            data shouldBe Some("hello world")
        }

        it("should return None when first parameter is none") {
            val data = Option.map2(None, Some("world")) { a, b -> "$a $b" }

            data shouldBe None
        }
    }

    describe("sequence") {
        it("should return Some when all list values have a value") {
            val data = LinkedList.of(Some(1), Some(2), Some(3))
            val result = Option.sequence(data)

            result shouldBe Some(LinkedList.of(1, 2, 3))
        }

        it("should return None when a list contains a None") {
            val data = LinkedList.of(Some(1), None, Some(3))
            val result = Option.sequence(data)

            result shouldBe None
        }
    }

    describe("traverse") {
        it("should return a mapped Option of a traversed list") {
            val data = LinkedList.of("1", "2", "3")
            val result = Option.traverse(data) { a ->
                try {
                    Some(a.toInt())
                } catch (err: Error) {
                    None
                }
            }

            result shouldBe Some(Cons(1, Cons(2, Cons(3, Nil))))
        }
    }
})
