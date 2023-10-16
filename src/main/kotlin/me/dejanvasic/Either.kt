package me.dejanvasic

sealed class Either<out E, out A> {
    companion object {
        fun <E, A, B, C> map2(ae: Either<E, A>, be: Either<E, B>, f: (A, B) -> C): Either<E, C> {
            return ae.flatMap { a -> be.flatMap { b -> Right(f(a, b)) } }
        }


        fun <E, A, B> traverse(xs: LinkedList<A>, f: (A) -> Either<E, B>): Either<E, LinkedList<B>> {
            return when (xs) {
                is Nil -> Right(Nil)
                is Cons -> map2(f(xs.head), traverse(xs.tail, f)) { b, xb -> Cons(b, xb) }
            }
        }

        fun mean(xs: LinkedList<Double>): Either<String, Double> {
            return when (xs) {
                is Nil -> Left("List cannot be empty to produce a mean value")
                is Cons -> Right(LinkedList.sum(xs) / LinkedList.length(xs))
            }
        }


        fun <A> catches(a: () -> A): Either<Exception, A> {
            return try {
                Right(a())
            } catch (e: Exception) {
                Left(e)
            }
        }
    }
}

data class Left<out E>(val value: E) : Either<E, Nothing>()

data class Right<out A>(val value: A) : Either<Nothing, A>()

fun <E, A, B> Either<E, A>.map(f: (A) -> B): Either<E, B> {
    return when (this) {
        is Left -> this
        is Right -> Right(f(this.value))
    }
}

fun <E, A, B> Either<E, A>.flatMap(f: (A) -> Either<E, B>): Either<E, B> {
    return when (this) {
        is Left -> this
        is Right -> f(this.value)
    }
}

fun <E, A> Either<E, A>.orElse(f: () -> Either<E, A>): Either<E, A> {
    return when (this) {
        is Left -> f()
        is Right -> this
    }
}
