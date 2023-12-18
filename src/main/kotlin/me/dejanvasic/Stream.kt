package me.dejanvasic


data class ConsStream<out A>(
    val head: () -> A,
    val tail: () -> Stream<A>,
) : Stream<A>()

object EmptyStream : Stream<Nothing>()


sealed class Stream<out A> {
    companion object {
        fun <A> of(vararg xs: A): Stream<A> {
            return if (xs.isEmpty()) emptyStream()
            else consStream({ xs[0] }, { of(*xs.sliceArray(1 until xs.size)) })
        }
    }
}

fun <A> consStream(hd: () -> A, tl: () -> Stream<A>): Stream<A> {
    val head: A by lazy(hd)
    val tail: Stream<A> by lazy(tl)
    return ConsStream({ head }, { tail })
}

fun <A> emptyStream(): Stream<A> = EmptyStream

fun <A> Stream<A>.headOption(): Option<A> = when (this) {
    is EmptyStream -> None
    is ConsStream -> Some(head())
}

fun <A> Stream<A>.toList(): LinkedList<A> {
    tailrec fun go(xs: Stream<A>, acc: LinkedList<A>): LinkedList<A> {
        return when (xs) {
            is EmptyStream -> acc
            is ConsStream -> go(xs.tail(), Cons(xs.head(), acc))
        }
    }
    return LinkedList.reverse(go(this, Nil))
}

fun <A> Stream<A>.take(n: Int): Stream<A> {
    tailrec fun go(xs: Stream<A>, n: Int): Stream<A> {
        return when (xs) {
            is EmptyStream -> emptyStream()
            is ConsStream ->
                if (n == 0) emptyStream()
                else consStream(xs.head, { go(xs.tail(), n - 1) })
        }
    }
    return go(this, n)
}

fun <A> Stream<A>.drop(n: Int): Stream<A> {
    tailrec fun go(xs: Stream<A>, n: Int): Stream<A> {
        return when (xs) {
            is EmptyStream -> emptyStream()
            is ConsStream ->
                if (n == 0) xs
                else go(xs.tail(), n - 1)
        }
    }
    return go(this, n)
}

fun <A> Stream<A>.takeWhile(p: (A) -> Boolean): Stream<A> =
    foldRight({ emptyStream() }) { a, b -> if (p(a)) consStream({ a }, b) else b() }

fun <A> Stream<A>.length(): Int {
    tailrec fun go(xs: Stream<A>, count: Int): Int {
        return when (xs) {
            is EmptyStream -> count
            is ConsStream -> go(xs.tail(), count + 1)
        }
    }
    return go(this, 0)
}

tailrec fun <A, B> Stream<A>.foldRight(z: () -> B, f: (A, () -> B) -> B): B {
    return when (this) {
        is EmptyStream -> z()
        is ConsStream -> f(this.head()) { this.tail().foldRight(z, f) }
    }
}

fun <A> Stream<A>.forAll(p: (A) -> Boolean): Boolean {
    return foldRight({ true }) { a, b -> p(a) && b() }
}
