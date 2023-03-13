package chapter_three

fun main() {
    val data = MyList.of(1, 2, 3, 4, 5)

    val tailOfData = MyList.tail<Int>(data)
    println("TailOfData: $tailOfData")

    val withNewHead = MyList.setHead(data, 6)
    println("WithNewHead $withNewHead")

    val dropped = MyList.drop(data, 2)
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
}

sealed class MyList<out A> {
    companion object {
        fun <A> empty(): MyList<A> = Nil

        fun <A> of(vararg aa: A): MyList<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        fun <A> tail(aa: MyList<A>): MyList<A> {
            return when (aa) {
                is Nil -> throw IllegalStateException("List cannot have empty chapter_one.getTail")
                is Cons -> aa.tail
            }
        }

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

        tailrec fun <A> dropWhile(l: MyList<A>, f: (A) -> Boolean): MyList<A> {
            return when (l) {
                is Nil -> l
                is Cons -> {
                    if (f(l.head)) dropWhile(l.tail, f) else l
                }
            }
        }

        tailrec fun <A> init(l: MyList<A>): MyList<A> {
            return when (l) {
                is Nil -> throw IllegalStateException("Cannot init an empty list")
                is Cons -> {
                    if (l.tail is Cons) init(l.tail) else l
                }
            }
        }

        fun <A> setHead(xs: MyList<A>, x: A): MyList<A> {
            return when (xs) {
                is Nil -> throw IllegalStateException("Cannot replace chapter_one.getHead of a Nil list")
                is Cons -> Cons(x, xs.tail)
            }
        }

        fun sum(ints: MyList<Int>): Int =
            foldLeft(ints, 0) { x, y -> x + y }

        fun product(dbs: MyList<Double>): Double =
            foldLeft(dbs, 1.toDouble()) { x, y -> x * y }

        fun <A> length(xs: MyList<A>): Int =
            foldLeft(xs, 0) { prev, _ -> prev + 1 }

        fun <A> reverse(xs: MyList<A>): MyList<A> =
            foldLeft(xs, empty()) { prev, curr -> Cons(curr, prev) }

        fun <A> append(a1: MyList<A>, a2: MyList<A>): MyList<A> =
            foldRight(a1, a2) { x, y -> Cons(x, y) }

        fun <A> concat(xxs: MyList<MyList<A>>): MyList<A> =
            foldRight(xxs, empty()) { x, y -> append(x, y) }

        fun increment(ints: MyList<Int>): MyList<Int> =
            foldRight(ints, empty()) { a, b -> Cons(a + 1, b) }

        private fun <A, B> foldRight(xs: MyList<A>, z: B, f: (A, B) -> B): B =
            when (xs) {
                is Nil -> z
                is Cons -> f(xs.head, foldRight(xs.tail, z, f))
            }

        private tailrec fun <A, B> foldLeft(xs: MyList<A>, z: B, f: (B, A) -> B): B {
            return when (xs) {
                is Nil -> z
                is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
            }
        }

        private fun <A, B> foldLeftR(xs: MyList<A>, z: B, f: (B, A) -> B): B {
            return foldRight(xs,
                { b: B -> b },
                { acc, g ->
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
