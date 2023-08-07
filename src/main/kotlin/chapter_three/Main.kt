package chapter_three

fun main() {
    val data = MyList.of(1, 2, 3, 4, 5)

    val tailOfData = MyList.tail(data)
    println("TailOfData: $tailOfData")

    val withNewHead = MyList.setHead(data, 6)
    println("WithNewHead $withNewHead")

    val dropped = MyList.drop(data, 4)
    println("Dropped: $dropped")

    val droppedConditional = MyList.dropWhile(MyList.of(1, 4, 2, 5)) { d -> d < 4 }
    println("DroppedConditional: $droppedConditional")

    val lastElement = MyList.init(data)
    println("LastElement: $lastElement")

    val summed = MyList.sum(data)
    println("Summed: $summed")

    val produceOfValues = MyList.product(MyList.of(2.toDouble(), 3.toDouble(), 6.toDouble()))
    println("Product: $produceOfValues")

    println("Length: ${MyList.length(data)}")

    println("Reverse: ${MyList.reverse(data)}")

    println("Append: ${MyList.append(data, MyList.of(10))}")

    val multiList = MyList.of(MyList.of("one", "two"), MyList.of("three", "four"))
    println("Concat: ${MyList.concat(multiList)}")

    println("Increment: ${MyList.increment(data)}")

    println("ToString: ${MyList.doubleToString(MyList.of(5.toDouble()))}")

    val mapped = MyList.map(data) { a -> Person(a * 3) }
    println("Mapped multiplied $mapped")

    val filtered = MyList.filter(data) { a -> a > 2 }
    println("Filtered larger than 2: $filtered")
}

data class Person(val points: Int)

sealed class MyList<out A> {
    companion object {
        fun <A> empty(): MyList<A> = Nil

        fun <A> of(vararg aa: A): MyList<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        // Exercise 3.1
        fun <A> tail(aa: MyList<A>): MyList<A> {
            return when (aa) {
                is Nil -> throw IllegalStateException("List cannot have empty chapter_one.getTail")
                is Cons -> aa.tail
            }
        }

        // Exercise 3.2
        fun <A> setHead(xs: MyList<A>, x: A): MyList<A> {
            return when (xs) {
                is Nil -> throw IllegalStateException("Cannot replace chapter_one.getHead of a Nil list")
                is Cons -> Cons(x, xs.tail)
            }
        }

        // Exercise 3.3
        tailrec fun <A> drop(l: MyList<A>, n: Int): MyList<A> {
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
        tailrec fun <A> dropWhile(xs: MyList<A>, f: (A) -> Boolean): MyList<A> {
            return when (xs) {
                is Nil -> xs
                is Cons -> {
                    if (f(xs.head)) dropWhile(xs.tail, f)
                    else xs
                }
            }
        }

        // Exercise 3.5
        tailrec fun <A> init(l: MyList<A>): MyList<A> {
            return when (l) {
                is Nil -> throw IllegalStateException("Cannot init an empty list")
                is Cons -> {
                    if (l.tail is Cons) init(l.tail) else l
                }
            }
        }

        // Exercise 3.10
        fun sum(ints: MyList<Int>): Int = foldLeft(ints, 0) { x, y -> x + y }

        // Exercise 3.10
        fun product(dbs: MyList<Double>): Double = foldLeft(dbs, 1.toDouble()) { x, y -> x * y }

        // Exercise 3.8
        fun <A> length(xs: MyList<A>): Int = foldLeft(xs, 0) { prev, _ -> prev + 1 }

        // Exercise 3.11
        fun <A> reverse(xs: MyList<A>): MyList<A> = foldLeft(xs, empty()) { prev, curr -> Cons(curr, prev) }

        // Exercise 3.13
        fun <A> append(a1: MyList<A>, a2: MyList<A>): MyList<A> = foldRight(a1, a2) { x, y -> Cons(x, y) }

        // Exercise 3.14
        fun <A> concat(xxs: MyList<MyList<A>>): MyList<A> = foldRight(xxs, empty()) { x, y -> append(x, y) }

        // Exercise 3.15
        fun increment(ints: MyList<Int>): MyList<Int> = foldRight(ints, empty()) { a, b -> Cons(a + 1, b) }

        // Exercise 3.16
        fun doubleToString(doubles: MyList<Double>): MyList<String> =
            foldRight(doubles, empty()) { a, b -> Cons(a.toString(), b) }

        // Exercise 3.17
        fun <A, B> map(xs: MyList<A>, f: (A) -> B): MyList<B> = foldRight(xs, empty()) { x, y -> Cons(f(x), y) }

        // Exercise 3.18
        fun <A> filter(xs: MyList<A>, f: (A) -> Boolean): MyList<A> = foldRight(xs, empty()) { x, y ->
            if (f(x)) Cons(x, y)
            else y
        }


        private fun <A, B> foldRight(xs: MyList<A>, z: B, f: (A, B) -> B): B = when (xs) {
            is Nil -> z
            is Cons -> f(xs.head, foldRight(xs.tail, z, f))
        }

        // Exercise 3.9
        private tailrec fun <A, B> foldLeft(xs: MyList<A>, z: B, f: (B, A) -> B): B {
            return when (xs) {
                is Nil -> z
                is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
            }
        }

        // Exercise 3.12
        private fun <A, B> foldLeftR(xs: MyList<A>, z: B, f: (B, A) -> B): B {
            return foldRight(xs, { b: B -> b }, { acc, g ->
                { b ->
                    g(f(b, acc))
                }
            })(z)
        }
    }
}

object Nil : MyList<Nothing>()
data class Cons<out A>(val head: A, val tail: MyList<A>) : MyList<A>()
typealias Identity<B> = (B) -> B
