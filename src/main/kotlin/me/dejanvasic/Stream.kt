package me.dejanvasic


data class StreamCons<out A>(
    val head: () -> A,
    val tail: () -> Stream<A>,
) : Stream<A>()

object StreamEmpty : Stream<Nothing>()

sealed class Stream<out A> {
    companion object {
        fun <A> cons(hd: () -> A, tl: () -> Stream<A>): Stream<A> {
            val head: A by lazy(hd)
            val tail: Stream<A> by lazy(tl)
            return StreamCons({ head }, { tail })
        }

        fun <A> emptyStream(): Stream<A> = StreamEmpty

        fun <A> of(vararg xs: A): Stream<A> {
            return if (xs.isEmpty()) emptyStream()
            else cons({ xs[0] }, { of(*xs.sliceArray(1 until xs.size)) })
        }

        fun <A> constant(n: A): Stream<A> {
            //return cons({ n }, { constant(n) })
            return unfold(n) { a -> Some(Pair(a, a)) }
        }

        fun from(n: Int): Stream<Int> {
            //return cons({ n }, { from(n + 1) })
            return unfold(n) { a -> Some(Pair(a, a + 1)) }
        }

        fun fibs(): Stream<Int> {
            return unfold(0 to 1) { (curr, next) ->
                Some(curr to (next to (curr + next)))
            }
        }

        fun <A, S> unfold(z: S, f: (S) -> Option<Pair<A, S>>): Stream<A> {
            return f(z).map { pair ->
                cons({ pair.first }, { unfold(pair.second, f) })
            }.getOrElse {
                emptyStream()
            }
        }
    }
}


fun <A> Stream<A>.headOption(): Option<A> = when (this) {
    is StreamEmpty -> None
    is StreamCons -> Some(head())
}

fun <A> Stream<A>.toList(): LinkedList<A> {
    tailrec fun go(xs: Stream<A>, acc: LinkedList<A>): LinkedList<A> {
        return when (xs) {
            is StreamEmpty -> acc
            is StreamCons -> go(xs.tail(), Cons(xs.head(), acc))
        }
    }
    return LinkedList.reverse(go(this, Nil))
}

fun <A> Stream<A>.take(n: Int): Stream<A> {
    tailrec fun go(xs: Stream<A>, n: Int): Stream<A> {
        return when (xs) {
            is StreamEmpty -> Stream.emptyStream()
            is StreamCons -> if (n == 0) Stream.emptyStream()
            else Stream.cons(xs.head, { go(xs.tail(), n - 1) })
        }
    }
    return go(this, n)
}

fun <A> Stream<A>.drop(n: Int): Stream<A> {
    tailrec fun go(xs: Stream<A>, n: Int): Stream<A> {
        return when (xs) {
            is StreamEmpty -> Stream.emptyStream()
            is StreamCons -> if (n == 0) xs
            else go(xs.tail(), n - 1)
        }
    }
    return go(this, n)
}

fun <A> Stream<A>.takeWhile(p: (A) -> Boolean): Stream<A> {
    return foldRight({ Stream.emptyStream() }) { a, b -> if (p(a)) Stream.cons({ a }, b) else b() }
}

fun <A> Stream<A>.length(): Int {
    tailrec fun go(xs: Stream<A>, count: Int): Int {
        return when (xs) {
            is StreamEmpty -> count
            is StreamCons -> go(xs.tail(), count + 1)
        }
    }
    return go(this, 0)
}

tailrec fun <A, B> Stream<A>.foldRight(z: () -> B, f: (A, () -> B) -> B): B {
    return when (this) {
        is StreamEmpty -> z()
        is StreamCons -> f(this.head()) { this.tail().foldRight(z, f) }
    }
}

fun <A> Stream<A>.forAll(p: (A) -> Boolean): Boolean {
    return foldRight({ true }) { a, b -> p(a) && b() }
}

fun <A, B> Stream<A>.map(f: (A) -> B): Stream<B> {
    return foldRight({ Stream.emptyStream() }) { a, b -> Stream.cons({ f(a) }, b) }
}

fun <A> Stream<A>.append(sa: Stream<A>): Stream<A> {
    return foldRight({ sa }) { a, b -> Stream.cons({ a }, b) }
}

fun <A, B> Stream<A>.flatMap(f: (A) -> Stream<B>): Stream<B> {
    return foldRight({ Stream.emptyStream() }) { h, t -> f(h).append(t()) }
}
