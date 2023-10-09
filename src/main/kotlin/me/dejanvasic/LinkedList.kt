package me.dejanvasic

object Nil : LinkedList<Nothing>()
data class Cons<out A>(val head: A, val tail: LinkedList<A>) : LinkedList<A>()

sealed class LinkedList<out A> {
    companion object {
        fun <A> empty(): LinkedList<A> = Nil

        fun <A> of(vararg aa: A): LinkedList<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        fun <A> tail(aa: LinkedList<A>): LinkedList<A> {
            return when (aa) {
                is Nil -> throw IllegalStateException("List cannot have empty chapter_one.getTail")
                is Cons -> aa.tail
            }
        }

        fun <A> setHead(xs: LinkedList<A>, x: A): LinkedList<A> {
            return when (xs) {
                is Nil -> throw IllegalStateException("Cannot replace chapter_one.getHead of a Nil list")
                is Cons -> Cons(x, xs.tail)
            }
        }

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

        tailrec fun <A> dropWhile(xs: LinkedList<A>, f: (A) -> Boolean): LinkedList<A> {
            return when (xs) {
                is Nil -> xs
                is Cons -> {
                    if (f(xs.head)) dropWhile(xs.tail, f)
                    else xs
                }
            }
        }

        tailrec fun <A> init(l: LinkedList<A>): LinkedList<A> {
            return when (l) {
                is Nil -> throw IllegalStateException("Cannot init an empty list")
                is Cons -> {
                    if (l.tail is Cons) init(l.tail) else l
                }
            }
        }

        fun sum(ints: LinkedList<Int>): Int = foldLeft(ints, 0) { x, y -> x + y }

        fun product(dbs: LinkedList<Double>): Double = foldLeft(dbs, 1.toDouble()) { x, y -> x * y }

        fun <A> length(xs: LinkedList<A>): Int = foldLeft(xs, 0) { prev, _ -> prev + 1 }

        fun <A> reverse(xs: LinkedList<A>): LinkedList<A> = foldLeft(xs, empty()) { prev, curr -> Cons(curr, prev) }

        fun <A> append(a1: LinkedList<A>, a2: LinkedList<A>): LinkedList<A> = foldRight(a1, a2) { x, y -> Cons(x, y) }

        fun <A> concat(xxs: LinkedList<LinkedList<A>>): LinkedList<A> = foldRight(xxs, empty()) { x, y -> append(x, y) }

        fun increment(ints: LinkedList<Int>): LinkedList<Int> = foldRight(ints, empty()) { a, b -> Cons(a + 1, b) }

        fun doubleToString(doubles: LinkedList<Double>): LinkedList<String> =
            foldRight(doubles, empty()) { a, b -> Cons(a.toString(), b) }

        fun <A, B> map(xs: LinkedList<A>, f: (A) -> B): LinkedList<B> = foldRight(xs, empty()) { x, y -> Cons(f(x), y) }

        fun <A> filter(xs: LinkedList<A>, f: (A) -> Boolean): LinkedList<A> = foldRight(xs, empty()) { x, y ->
            if (f(x)) Cons(x, y)
            else y
        }

        tailrec fun <A, B> foldRight(xs: LinkedList<A>, z: B, f: (A, B) -> B): B = when (xs) {
            is Nil -> z
            is Cons -> f(xs.head, foldRight(xs.tail, z, f))
        }

        tailrec fun <A, B> foldLeft(xs: LinkedList<A>, z: B, f: (B, A) -> B): B {
            return when (xs) {
                is Nil -> z
                is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
            }
        }
    }
}
