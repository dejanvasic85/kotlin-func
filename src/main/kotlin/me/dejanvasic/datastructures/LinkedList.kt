package me.dejanvasic.datastructures

fun main() {
    val data = LinkedList.of(1, 2, 3, 4, 5)

    val tailOfData = LinkedList.tail(data)
    println("TailOfData: $tailOfData")

    val withNewHead = LinkedList.setHead(data, 6)
    println("WithNewHead $withNewHead")

    val dropped = LinkedList.drop(data, 4)
    println("Dropped: $dropped")

    val droppedConditional = LinkedList.dropWhile(LinkedList.of(1, 4, 2, 5)) { d -> d < 4 }
    println("DroppedConditional: $droppedConditional")

    val lastElement = LinkedList.init(data)
    println("LastElement: $lastElement")

    val summed = LinkedList.sum(data)
    println("Summed: $summed")

    val produceOfValues = LinkedList.product(LinkedList.of(2.toDouble(), 3.toDouble(), 6.toDouble()))
    println("Product: $produceOfValues")

    println("Length: ${LinkedList.length(data)}")

    println("Reverse: ${LinkedList.reverse(data)}")

    println("Append: ${LinkedList.append(data, LinkedList.of(10))}")

    val multiList = LinkedList.of(LinkedList.of("one", "two"), LinkedList.of("three", "four"))
    println("Concat: ${LinkedList.concat(multiList)}")

    println("Increment: ${LinkedList.increment(data)}")

    println("ToString: ${LinkedList.doubleToString(LinkedList.of(5.toDouble()))}")

    val mapped = LinkedList.map(data) { a -> Person(a * 3) }
    println("Mapped multiplied $mapped")

    val filtered = LinkedList.filter(data) { a -> a > 2 }
    println("Filtered larger than 2: $filtered")
}

data class Person(val points: Int)

sealed class LinkedList<out A> {
    companion object {
        fun <A> empty(): LinkedList<A> = Nil

        fun <A> of(vararg aa: A): LinkedList<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        // Exercise 3.1
        // Removes the first item from the list (keeps the tail)
        fun <A> tail(aa: LinkedList<A>): LinkedList<A> {
            return when (aa) {
                is Nil -> throw IllegalStateException("List cannot have empty chapter_one.getTail")
                is Cons -> aa.tail
            }
        }

        // Exercise 3.2
        // Removes the current head with a new value
        fun <A> setHead(xs: LinkedList<A>, x: A): LinkedList<A> {
            return when (xs) {
                is Nil -> throw IllegalStateException("Cannot replace chapter_one.getHead of a Nil list")
                is Cons -> Cons(x, xs.tail)
            }
        }

        // Exercise 3.3
        tailrec fun <A> drop(l: LinkedList<A>, n: Int): LinkedList<A> {
            if (n == 0) {
                return l
            }

            return when (l) {
                is Nil -> throw IllegalStateException("Unable to drop items from an empty list")
                is Cons -> {
                    drop(l.tail, n - 1)
                }
            }
        }

        // Exercise 3.4
        tailrec fun <A> dropWhile(xs: LinkedList<A>, f: (A) -> Boolean): LinkedList<A> {
            return when (xs) {
                is Nil -> xs
                is Cons -> {
                    if (f(xs.head)) dropWhile(xs.tail, f)
                    else xs
                }
            }
        }

        // Exercise 3.5
        tailrec fun <A> init(l: LinkedList<A>): LinkedList<A> {
            return when (l) {
                is Nil -> throw IllegalStateException("Cannot init an empty list")
                is Cons -> {
                    if (l.tail is Cons) init(l.tail) else l
                }
            }
        }

        // Exercise 3.10
        fun sum(ints: LinkedList<Int>): Int = foldLeft(ints, 0) { x, y -> x + y }

        // Exercise 3.10
        fun product(dbs: LinkedList<Double>): Double = foldLeft(dbs, 1.toDouble()) { x, y -> x * y }

        // Exercise 3.8
        fun <A> length(xs: LinkedList<A>): Int = foldLeft(xs, 0) { prev, _ -> prev + 1 }

        // Exercise 3.11
        fun <A> reverse(xs: LinkedList<A>): LinkedList<A> = foldLeft(xs, empty()) { prev, curr -> Cons(curr, prev) }

        // Exercise 3.13
        fun <A> append(a1: LinkedList<A>, a2: LinkedList<A>): LinkedList<A> = foldRight(a1, a2) { x, y -> Cons(x, y) }

        // Exercise 3.14
        fun <A> concat(xxs: LinkedList<LinkedList<A>>): LinkedList<A> = foldRight(xxs, empty()) { x, y -> append(x, y) }

        // Exercise 3.15
        fun increment(ints: LinkedList<Int>): LinkedList<Int> = foldRight(ints, empty()) { a, b -> Cons(a + 1, b) }

        // Exercise 3.16
        fun doubleToString(doubles: LinkedList<Double>): LinkedList<String> =
            foldRight(doubles, empty()) { a, b -> Cons(a.toString(), b) }

        // Exercise 3.17
        fun <A, B> map(xs: LinkedList<A>, f: (A) -> B): LinkedList<B> = foldRight(xs, empty()) { x, y -> Cons(f(x), y) }

        // Exercise 3.18
        fun <A> filter(xs: LinkedList<A>, f: (A) -> Boolean): LinkedList<A> = foldRight(xs, empty()) { x, y ->
            if (f(x)) Cons(x, y)
            else y
        }


        private fun <A, B> foldRight(xs: LinkedList<A>, z: B, f: (A, B) -> B): B = when (xs) {
            is Nil -> z
            is Cons -> f(xs.head, foldRight(xs.tail, z, f))
        }

        // Exercise 3.9
        private tailrec fun <A, B> foldLeft(xs: LinkedList<A>, z: B, f: (B, A) -> B): B {
            return when (xs) {
                is Nil -> z
                is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
            }
        }

        // Exercise 3.12
        private fun <A, B> foldLeftR(xs: LinkedList<A>, z: B, f: (B, A) -> B): B {
            return foldRight(xs, { b: B -> b }, { acc, g ->
                { b ->
                    g(f(b, acc))
                }
            })(z)
        }
    }
}

object Nil : LinkedList<Nothing>()
data class Cons<out A>(val head: A, val tail: LinkedList<A>) : LinkedList<A>()
typealias Identity<B> = (B) -> B
